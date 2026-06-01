// TODO
package com.drake.droidblox.roblox

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import android.os.ParcelFileDescriptor
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.drake.IRobloxSession
import com.drake.droidblox.service.Service
import com.drake.logger.AndroidLogger
import com.drake.logger.Logger
import org.junit.Test
import org.junit.runner.RunWith
import java.io.BufferedWriter
import java.io.FileOutputStream
import java.io.OutputStreamWriter

@RunWith(AndroidJUnit4::class)
class ServiceTest {
    companion object {
        private const val TAG = "ServiceTest"
    }

    private val logger: Logger = AndroidLogger
    private val context: Context = ApplicationProvider.getApplicationContext()
    private lateinit var mService: IRobloxSession

    private val serviceConn = object : ServiceConnection {
        override fun onServiceConnected(
            name: ComponentName?,
            service: IBinder?
        ) {
            logger.d(TAG, "service connected")
            mService = IRobloxSession.Stub.asInterface(service)
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            logger.d(TAG, "service disconnected")
        }
    }

    fun bindToService() =
        context.bindService(
            Intent(context, Service::class.java),
            serviceConn,
            Context.BIND_AUTO_CREATE
        )

    private fun BufferedWriter.writeln(text: String) {
        this.write(text)
        this.newLine()
    }

    @Test fun testAll() {
        logger.i(TAG, "Binding to service")
        bindToService()

        logger.i(TAG, "getFFlags: ${mService.getFFlags()}")
        logger.i(TAG, "getTextureMods: ${mService.getTextureMods()}")
        logger.i(TAG, "shouldApplyTextureMods: ${mService.shouldApplyTextureMods()}")
        logger.i(TAG, "textureModsAlreadyConfigured: ${mService.textureModsAlreadyConfigured()}")

        val pipe = ParcelFileDescriptor.createPipe()
        val readPipe = pipe[0]
        val writePipe = pipe[1]
        logger.i(TAG, "Passing PFD to pfdForRobloxLogcat")
        mService.pfdForRobloxLogcat(readPipe)

        writePipe.use { descriptor ->
            FileOutputStream(descriptor.fileDescriptor).use { outputStream ->
                OutputStreamWriter(outputStream).use { outputStreamWriter ->
                    BufferedWriter(outputStreamWriter).use { writer ->
                        logger.i(TAG, "Passing game joining entry")
                        writer.writeln("[FLog::Output] ! Joining game '6dc9f5fa-d7ff-4f39-b7f3-c8ca11b5afc1' place 73423035276895 at 10.30.1.41")

                        logger.i(TAG, "Passing joining universe entry")
                        writer.writeln("[FLog::GameJoinLoadTime] Report game_join_loadtime: sid:58b78a1e-d384-485e-a706-9ecad704fc0a, clienttime:1776066497.2269999981, join_time:0.64354772600000131888, referral_page:ExperienceDetailsPage, placeid:73423035276895, userid:2465212868, universeid:9355318793")

                        logger.i(TAG, "Passing udmux entry")
                        writer.writeln("[FLog::Output] Connecting to UDMUX server 128.116.13.34:51506, and RCC server 10.30.1.41:51506")

                        logger.i(TAG, "Passing Passing game joined entry")
                        writer.writeln("[FLog::Network] serverId:")

                        //logger.i(TAG, "Passing game message entry")
                        // TODO

                        logger.i(TAG, "Passing game disconnected entry")
                        writer.writeln("[FLog::Network] Time to disconnect replication data:")
                    }
                }
            }
        }

        logger.i(TAG, "Unbinding (simulating that the Roblox app died)")
        context.unbindService(serviceConn)
    }
}