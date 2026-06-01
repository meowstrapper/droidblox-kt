package com.drake.droidblox.texturemods

import android.content.Context
import android.content.res.AssetManager
import android.net.Uri
import android.os.Environment
import android.os.ParcelFileDescriptor
import android.webkit.MimeTypeMap
import android.widget.Toast
import androidx.activity.result.ActivityResultCaller
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.drake.droidblox.datastores.ModsManager
import com.drake.droidblox.texturemods.models.CustomEmoji
import com.drake.droidblox.texturemods.models.CustomMouseCursor
import com.drake.logger.Logger
import dagger.hilt.android.qualifiers.ApplicationContext
import io.ktor.client.HttpClient
import io.ktor.client.request.prepareGet
import io.ktor.client.statement.bodyAsChannel
import io.ktor.utils.io.jvm.javaio.toInputStream
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.io.File
import java.io.FileOutputStream
import java.util.zip.ZipEntry
import java.util.zip.ZipInputStream
import java.util.zip.ZipOutputStream
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.concurrent.thread

@Singleton
class TextureMods @Inject constructor(
    val logger: Logger,
    val httpClient: HttpClient,
    val modsManager: ModsManager,
    @ApplicationContext val context: Context
) {
    companion object {
        private const val TAG = "TextureMods"
    }

    private lateinit var openFontFileLauncher: ActivityResultLauncher<Array<String>>
    private lateinit var openModZipFileLauncher: ActivityResultLauncher<Array<String>>

    val assetManager: AssetManager = context.assets

    val dbAssetOverlaysFolder = File(context.filesDir, "assetOverlays")
    val userAssetOverlaysFolder = File(context.filesDir, "userAssetOverlays")

    private val fontHeaders = mapOf(
        "ttf" to byteArrayOf(0x00, 0x01, 0x00, 0x00),
        "otf" to byteArrayOf(0x4F, 0x54, 0x54, 0x4F),
        "ttc" to byteArrayOf(0x74, 0x74, 0x63, 0x66)
    )
    private val assetFoldersTarget = listOf("android", "content", "ExtraContent")

    fun ensureAssetOverlayFolderExists(): Boolean {
        return if (dbAssetOverlaysFolder.exists()) {
            true
        } else {
            dbAssetOverlaysFolder.mkdirs()
        }
    }

    private fun chooseModZip(uri: Uri) = context.contentResolver.openInputStream(uri).use { inputStream ->
        if (!userAssetOverlaysFolder.exists()) {
            userAssetOverlaysFolder.mkdirs()
        }

        var assetFoldersTargetFound = false

        ZipInputStream(inputStream).use { zipInputStream ->
            var entry = zipInputStream.nextEntry

            while (entry != null) {
                val entryName = entry.name
                val match = assetFoldersTarget.find { entryName.contains("$it/") || entryName.endsWith(it) }
                if (match != null) {
                    assetFoldersTargetFound = true
                    val path = entryName.substring(entryName.indexOf(match))
                    val outFile = File(userAssetOverlaysFolder, path)

                    if (entry.isDirectory) {
                        outFile.mkdirs()
                    } else {
                        outFile.parentFile?.mkdirs()

                        logger.d(TAG, "Extracting $path from ZIP")
                        FileOutputStream(outFile).use { fileOutputStream ->
                            zipInputStream.copyTo(fileOutputStream)
                        }
                    }
                }
                zipInputStream.closeEntry()
                entry = zipInputStream.nextEntry
            }
        }

        if (!assetFoldersTargetFound) {
            logger.w(TAG, "Folders for asset overlays aren't found in the ZIP file!")
            Toast.makeText(context, "Folders for asset overlays aren't found in the ZIP file!", Toast.LENGTH_SHORT).show()
        }
    }

    fun promptChooseModZip() = openModZipFileLauncher.launch(arrayOf(
        "application/zip"
    ))

    suspend fun replaceCursor(customMouseCursor: CustomMouseCursor) {
        ensureAssetOverlayFolderExists()

        val cursorFiles = listOf("ArrowCursor.png", "ArrowFarCursor.png")

        val versionFolder = when (customMouseCursor) {
            CustomMouseCursor.DEFAULT -> {
                logger.d(TAG, "Removing cursors on asset overlays")
                cursorFiles.forEach {
                    File(dbAssetOverlaysFolder, "content/textures/Cursors/KeyboardMouse/$it").delete()
                }
                return
            }
            CustomMouseCursor.PRIOR2006 -> "2006"
            CustomMouseCursor.PRIOR2013 -> "2013"
        }
        logger.d(TAG, "Copying $versionFolder mouse cursor")
        cursorFiles.forEach {
            assetManager.open("cursor/$versionFolder/$it").use { inputStream ->
                val cursorFile = File(dbAssetOverlaysFolder, "content/textures/Cursors/KeyboardMouse/$it")
                cursorFile.parentFile!!.mkdirs()
                inputStream.copyTo(cursorFile.outputStream())
            }
        }
        toggleTextureModsChanged()
    }

    suspend fun useOldAvatarBackground(replace: Boolean) {
        ensureAssetOverlayFolderExists()

        val avatarBackground = File(dbAssetOverlaysFolder, "ExtraContent/places/Mobile.rbxl")

        if (replace) {
            logger.d(TAG, "Replacing with old avatar background")

            assetManager.open("OldAvatarBackground.rbxl").use { inputStream ->
                avatarBackground.parentFile!!.mkdirs()
                inputStream.copyTo(avatarBackground.outputStream())
            }
        } else {
            logger.d(TAG, "Removing overwritten avatar background")

            avatarBackground.delete()
        }
        toggleTextureModsChanged()
    }

    suspend fun useOldCharacterSounds(replace: Boolean) {
        ensureAssetOverlayFolderExists()

        if (replace) {
            logger.d(TAG, "Replacing with old character sounds")

            mapOf(
                "content/sounds/action_footsteps_plastic.mp3" to "sounds/OldWalk.mp3",
                "content/sounds/action_jump.mp3" to "sounds/OldJump.mp3",
                "content/sounds/action_get_up.mp3" to "sounds/OldGetUp.mp3"
            ).forEach { sound, replaceWith ->
                assetManager.open(replaceWith).use { inputStream ->
                    val fileToReplace = File(dbAssetOverlaysFolder, sound)
                    fileToReplace.parentFile!!.mkdirs()
                    inputStream.copyTo(fileToReplace.outputStream())
                }
            }
            assetManager.open("sounds/Empty.mp3").use { inputStream ->
                listOf(
                    "content/sounds/action_falling.mp3",
                    "content/sounds/action_jump_land.mp3",
                    "content/sounds/action_swim.mp3",
                    "content/sounds/impact_water.mp3"
                ).forEach { sound ->
                    val fileToReplace = File(dbAssetOverlaysFolder, sound)
                    fileToReplace.parentFile!!.mkdirs()
                    inputStream.copyTo(fileToReplace.outputStream())
                }
            }
        } else {
            logger.d(TAG, "Removing overwritten character sounds")

            listOf(
                "content/sounds/action_footsteps_plastic.mp3",
                "content/sounds/action_jump.mp3",
                "content/sounds/action_get_up.mp3",
                "content/sounds/action_falling.mp3",
                "content/sounds/action_jump_land.mp3",
                "content/sounds/action_swim.mp3",
                "content/sounds/impact_water.mp3"
            ).forEach {
                File(dbAssetOverlaysFolder, it).delete()
            }
        }
        toggleTextureModsChanged()

    }

    suspend fun replaceEmoji(customEmoji: CustomEmoji) {
        ensureAssetOverlayFolderExists()

        val emojiFont = File(dbAssetOverlaysFolder, "content/fonts/TwemojiMozilla.ttf")
        emojiFont.parentFile!!.mkdirs()

        val fontToUse = when (customEmoji) {
            CustomEmoji.DEFAULT -> {
                logger.d(TAG, "Removing custom emoji on asset overlays")
                emojiFont.delete()
                return
            }
            CustomEmoji.ANDROID -> "Android.ttf"
            CustomEmoji.CATMOJI -> "Catmoji.ttf"
            CustomEmoji.WINDOWS11 -> "Windows11.ttf"
            CustomEmoji.WINDOWS10 -> "Windows10.ttf"
            CustomEmoji.WINDOWS8 -> "Windows8.ttf"
        }

        assetManager.open("emojis/$fontToUse").use { inputStream ->
            inputStream.copyTo(emojiFont.outputStream())
        }
        toggleTextureModsChanged()
    }

    private suspend fun useCustomFont(uri: Uri) = context.contentResolver.openInputStream(uri).use { inputStream ->
        ensureAssetOverlayFolderExists()

        val extension = MimeTypeMap.getSingleton().getExtensionFromMimeType(context.contentResolver.getType(uri))

        val magicBuffer = ByteArray(4)
        inputStream!!.read(magicBuffer)

        if (!fontHeaders.containsKey(extension)) {
            logger.w(TAG, "Looks like a file with the extension $extension has been picked, but how?")
            return@use
        } else if (!magicBuffer.contentEquals(fontHeaders[extension])) {
            logger.d(TAG, "Font file $uri with extension $extension isn't supported")
            Toast.makeText(context, "Extension $extension isn't supported", Toast.LENGTH_SHORT).show()
            return@use
        }

        val fileToWrite = File(dbAssetOverlaysFolder, "content/fonts/CustomFont.ttf")
        fileToWrite.parentFile!!.mkdirs()
        inputStream.copyTo(fileToWrite.outputStream())

//        logger.d(TAG, "Requesting custom font patches")
//        val customFontPatches = githubApi.fetchLatestRelease(
//            owner = "meowstrapper",
//            repo = CUSTOM_FONTS_REPO
//        )

        val toDownload = "https://github.com/meowstrapper/customfontspatcher/releases/download/latest/patches.zip" //customFontPatches.assets[0].browserDownloadUrl
        logger.d(TAG, "Downloading $toDownload")
        httpClient.prepareGet(toDownload).execute { httpResponse ->
            httpResponse.bodyAsChannel().toInputStream().use { inputStream ->
                ZipInputStream(inputStream).use { zipInputStream ->
                    File(dbAssetOverlaysFolder, "content/fonts/families/").mkdirs()

                    var fontFile = zipInputStream.nextEntry
                    while (fontFile != null) {
                        logger.d(TAG, "Extracting ${fontFile.name}")
                        FileOutputStream(File(dbAssetOverlaysFolder, "content/fonts/families/${fontFile.name}")).use { outputStream ->
                            zipInputStream.copyTo(outputStream)
                        }

                        zipInputStream.closeEntry()
                        fontFile = zipInputStream.nextEntry
                    }
                }
            }
        }
        toggleTextureModsChanged()
    }

    fun promptUseCustomFont() = openFontFileLauncher.launch(arrayOf(
        "font/otf",
        "font/ttf",
        "font/ttc"
    ))

    fun initLaunchers(caller: ActivityResultCaller, scope: CoroutineScope) {
        openFontFileLauncher = caller.registerForActivityResult(
            contract = ActivityResultContracts.OpenDocument(),
            callback = { scope.launch(Dispatchers.IO) { useCustomFont(it!!) } }
        )
        openModZipFileLauncher = caller.registerForActivityResult(
            contract = ActivityResultContracts.OpenDocument(),
            callback = { scope.launch(Dispatchers.IO) { chooseModZip(it!!) } }
        )

    }

    suspend fun toggleTextureModsChanged(value: Boolean = false) =
        modsManager.set(
            key = ModsManager.TEXTURE_MODS_ALREADY_CONFIGURED,
            value = value
        )

    fun zipFolderForPFD(): ParcelFileDescriptor {
        val pipe = ParcelFileDescriptor.createPipe()
        val read = pipe[0]
        val write = pipe[1]

        thread {
            ParcelFileDescriptor.AutoCloseOutputStream(write).use { outputStream ->
                ZipOutputStream(outputStream).use { zipOutputStream ->
                    dbAssetOverlaysFolder.walkTopDown().forEach { file ->
                        if (file == dbAssetOverlaysFolder) return@forEach

                        val entryName = file.relativeTo(dbAssetOverlaysFolder).path
                        if (file.isDirectory) {
                            zipOutputStream.putNextEntry(ZipEntry("$entryName/"))
                        } else {
                            zipOutputStream.putNextEntry(ZipEntry(entryName))
                            file.inputStream().use { it.copyTo(zipOutputStream) }
                        }
                        zipOutputStream.closeEntry()
                    }
                }
            }
        }
        runBlocking { toggleTextureModsChanged(true) }

        return read
    }
}
