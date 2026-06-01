package com.drake.droidblox.ui.view.views.fastflags



import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.drake.droidblox.datastores.fastflags.FastFlagsManager
import com.drake.droidblox.ui.components.BasicScreen
import com.drake.droidblox.ui.components.ExtendedDropdown
import com.drake.droidblox.ui.components.ExtendedSwitch
import com.drake.droidblox.ui.components.ExtendedTextField
import com.drake.droidblox.ui.components.SectionText
import com.drake.droidblox.ui.view.views.fastflags.state.FFlagsScreenCallbacks
import com.drake.droidblox.ui.view.views.fastflags.vm.FFlagsScreenVM
import com.drake.droidblox.ui.view.views.fastflags.state.FFlagsScreenState

@Composable
fun FFlagsScreen( // todo what the fuck am i doing rn
    navController: NavController? = null,
    state: FFlagsScreenState = FFlagsScreenState(),
    callback: FFlagsScreenCallbacks = FFlagsScreenCallbacks()
) {
    BasicScreen(
        name = "Fast Flags",
        navController = navController,
        useLazyColumn = true,
        lazyColumnContents = {
        item { ExtendedSwitch(
            title = "Allow DroidBlox to manage Fast Flags",
            subtitle = "Disabling this will prevent anything configured here from being applied to Roblox.",
            enabled = state.applyFFlags,
            onClick = callback.applyFFlags
        ) }

        item { SectionText("Rendering") }

        item { ExtendedDropdown(
            title = "Anti-aliasing quality (MSAA)",
            subtitle = "Smoothens the jagged edges to make textures detailed.",
            default = state.forceMSAA,
            items = listOf(
                "Automatic",
                "1x",
                "2x",
                "4x",
            ),
            onChoose = callback.forceMSAA
        ) }
        item { ExtendedDropdown(
            title = "Rendering mode",
            subtitle = "Choose what rendering API to use for Roblox",
            default = state.renderingMode,
            items = listOf(
                "Automatic",
                "Vulkan",
                "OpenGL",
            ),
            onChoose = callback.renderingMode
        ) }
        item { ExtendedDropdown(
            title = "Texture quality",
            subtitle = "Choose what level of texture quality to render",
            default = state.textureQuality,
            items = listOf(
                "Automatic",
                "Level 0",
                "Level 1",
                "Level 2",
                "Level 3"
            ),
            onChoose = callback.renderingMode
        ) }
        item { ExtendedSwitch(
            title = "Override sky to solid gray",
            subtitle = "Overrides the sky into a solid gray color",
            enabled = state.overrideSkyToGray,
            onClick = callback.overrideSkyToGray
        ) }
        item { ExtendedSwitch( // TODO: Improve title and subtitle
            title = "Pause voxelizer",
            subtitle = "Pauses the voxelizer",
            enabled = state.pauseVoxelizer,
            onClick = callback.pauseVoxelizer
        ) }
        item { ExtendedTextField( // TODO: Improve title and subtitle
            title = "Override quality level",
            subtitle = "Overrides the quality level",
            default = state.overrideQualityLevel,
            onTextChange = callback.overrideQualityLevel,
            keyboardType = KeyboardType.Number
        ) }
        item { ExtendedTextField( // TODO: Improve title and subtitle
            title = "Minimum grass distance",
            subtitle = "Set the minimum distance of rendering grass",
            default = state.minGrassDistance,
            onTextChange = callback.minGrassDistance,
            keyboardType = KeyboardType.Number
        ) }
        item { ExtendedTextField( // TODO: Improve title and subtitle
            title = "Maximum grass distance",
            subtitle = "Set the maximum distance of rendering grass",
            default = state.maxGrassDistance,
            onTextChange = callback.maxGrassDistance,
            keyboardType = KeyboardType.Number
        ) }

        item { SectionText("Geometry") }

        item { ExtendedTextField( // TODO: Improve title and subtitle
            title = "LOD for Polygons",
            subtitle = "Overrides the LOD (Level of Detail) per stud",
            default = state.lod,
            onTextChange = callback.lod,
            keyboardType = KeyboardType.Number
        ) }
        item { ExtendedTextField( // TODO: Improve title and subtitle
            title = "LOD for Polygons L12",
            subtitle = "Overrides the LOD (Level of Detail) per stud",
            default = state.lod12,
            onTextChange = callback.lod12,
            keyboardType = KeyboardType.Number
        ) }
        item { ExtendedTextField( // TODO: Improve title and subtitle
            title = "LOD for Polygons L23",
            subtitle = "Overrides the LOD (Level of Detail) per stud",
            default = state.lod23,
            onTextChange = callback.lod23,
            keyboardType = KeyboardType.Number
        ) }
        item { ExtendedTextField( // TODO: Improve title and subtitle
            title = "LOD for Polygons L34",
            subtitle = "Overrides the LOD (Level of Detail) per stud",
            default = state.lod34,
            onTextChange = callback.lod34,
            keyboardType = KeyboardType.Number
        ) }

        item { SectionText("User Interface") }

        item { ExtendedTextField( // TODO: Improve title and subtitle
            title = "Grass movement reduced motion factor",
            subtitle = "Overrides the Grass movement reduced motion factor", // what the FUCK should i type
            default = state.grassMovementReduced,
            onTextChange = callback.grassMovementReduced,
            keyboardType = KeyboardType.Number
            )
        }
    }
)
}

@Composable
fun FFlagsScreenImpl(
    navController: NavController
) {
    val viewModel: FFlagsScreenVM = hiltViewModel()

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    FFlagsScreen(
        navController = navController,
        state = uiState,
        callback = FFlagsScreenCallbacks(
            applyFFlags = {
                viewModel.setSetting(
                    key = FastFlagsManager.APPLY_FAST_FLAGS,
                    value = it
                )
            },
            forceMSAA = {
                when (it) {
                    "Automatic" -> {
                        viewModel.editFFlag { delete("FIntDebugForceMSAASamples") }
                        viewModel.setSetting(
                            key = FastFlagsManager.ANTI_ALIASING_QUALITY,
                            value = it
                        )
                    }

                    "1x" -> {
                        viewModel.editFFlag { set("FIntDebugForceMSAASamples", "1") }
                        viewModel.setSetting(
                            key = FastFlagsManager.ANTI_ALIASING_QUALITY,
                            value = it
                        )
                    }

                    "2x" -> {
                        viewModel.editFFlag { set("FIntDebugForceMSAASamples", "2") }
                        viewModel.setSetting(
                            key = FastFlagsManager.ANTI_ALIASING_QUALITY,
                            value = it
                        )
                    }

                    "4x" -> {
                        viewModel.editFFlag { set("FIntDebugForceMSAASamples", "4") }
                        viewModel.setSetting(
                            key = FastFlagsManager.ANTI_ALIASING_QUALITY,
                            value = it
                        )
                    }
                }
            },
            renderingMode = {
                when (it) {
                    "Automatic" -> {
                        viewModel.editFFlag {
                            listOf(
                                "FFlagDebugGraphicsPreferVulkan",
                                "FFlagDebugGraphicsPreferOpenGL"
                            ).forEach { fflag ->
                                delete(fflag)
                            }
                        }
                        viewModel.setSetting(
                            key = FastFlagsManager.RENDERING_MODE,
                            value = it
                        )
                    }

                    "Vulkan" -> {
                        viewModel.editFFlag {
                            delete("FFlagDebugGraphicsPreferOpenGL")
                            set("FFlagDebugGraphicsPreferVulkan", "true")
                        }
                        viewModel.setSetting(
                            key = FastFlagsManager.RENDERING_MODE,
                            value = it
                        )
                    }

                    "OpenGL" -> {
                        viewModel.editFFlag {
                            delete("FFlagDebugGraphicsPreferVulkan")
                            set("FFlagDebugGraphicsPreferOpenGL", "true")
                        }
                        viewModel.setSetting(
                            key = FastFlagsManager.RENDERING_MODE,
                            value = it
                        )
                    }
                }
            },
            textureQuality = {
                when (it) {
                    "Automatic" -> {
                        viewModel.editFFlag {
                            listOf(
                                "FFlagDebugGraphicsPreferVulkan",
                                "FFlagDebugGraphicsPreferOpenGL"
                            ).forEach { fflag ->
                                delete(fflag)
                            }
                        }
                        viewModel.setSetting(
                            key = FastFlagsManager.RENDERING_MODE,
                            value = it
                        )
                    }

                    "Vulkan" -> {
                        viewModel.editFFlag {
                            delete("FFlagDebugGraphicsPreferOpenGL")
                            set("FFlagDebugGraphicsPreferVulkan", "true")
                        }
                        viewModel.setSetting(
                            key = FastFlagsManager.RENDERING_MODE,
                            value = it
                        )
                    }

                    "OpenGL" -> {
                        viewModel.editFFlag {
                            delete("FFlagDebugGraphicsPreferVulkan")
                            set("FFlagDebugGraphicsPreferOpenGL", "true")
                        }
                        viewModel.setSetting(
                            key = FastFlagsManager.RENDERING_MODE,
                            value = it
                        )
                    }
                }
            },
            overrideSkyToGray = {
                viewModel.editFFlag { set("FFlagDebugSkyGray", it.toString()) }
                viewModel.setSetting(
                    key = FastFlagsManager.OVERRIDE_SKY_TO_GRAY,
                    value = it
                )
            },
            pauseVoxelizer = {
                viewModel.editFFlag { set("DFFlagDebugPauseVoxelizer", it.toString()) }
                viewModel.setSetting(
                    key = FastFlagsManager.PAUSE_VOXELIZER,
                    value = it
                )
            },
            overrideQualityLevel = {
                viewModel.editFFlag { set("DFIntDebugFRMQualityLevelOverride", it) }
                viewModel.setSetting(
                    key = FastFlagsManager.OVERRIDE_QUALITY_LEVEL,
                    value = it
                )
            },
            minGrassDistance = {
                viewModel.editFFlag { set("FIntFRMMinGrassDistance", it) }
                viewModel.setSetting(
                    key = FastFlagsManager.MIN_GRASS_DISTANCE,
                    value = it
                )
            },
            maxGrassDistance = {
                viewModel.editFFlag { set("FIntFRMMaxGrassDistance", it) }
                viewModel.setSetting(
                    key = FastFlagsManager.MAX_GRASS_DISTANCE,
                    value = it
                )
            },
            lod = {
                viewModel.editFFlag { set("DFIntCSGLevelOfDetailSwitchingDistance", it) }
                viewModel.setSetting(
                    key = FastFlagsManager.LOD,
                    value = it
                )
            },
            lod12 = {
                viewModel.editFFlag { set("DFIntCSGLevelOfDetailSwitchingDistanceL12", it) }
                viewModel.setSetting(
                    key = FastFlagsManager.LOD12,
                    value = it
                )
            },
            lod23 = {
                viewModel.editFFlag { set("DFIntCSGLevelOfDetailSwitchingDistanceL23", it) }
                viewModel.setSetting(
                    key = FastFlagsManager.LOD23,
                    value = it
                )
            },
            lod34 = {
                viewModel.editFFlag { set("DFIntCSGLevelOfDetailSwitchingDistanceL12", it) }
                viewModel.setSetting(
                    key = FastFlagsManager.LOD34,
                    value = it
                )
            },
            grassMovementReduced = {
                viewModel.editFFlag { set("FIntGrassMovementReducedMotionFactor", it) }
                viewModel.setSetting(
                    key = FastFlagsManager.GRASS_MOVEMENT_REDUCED,
                    value = it
                )
            }
        )
    )
}

@Preview
@Composable
private fun PreviewFFlagsScreen() {
    FFlagsScreen()
}