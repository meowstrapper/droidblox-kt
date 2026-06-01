package com.drake.droidblox.ui.view.views.fastflags.state

data class FFlagsScreenState(
    val applyFFlags: Boolean = true,
    val forceMSAA: String = "Automatic",
    val renderingMode: String = "Automatic",
    val textureQuality: String = "Automatic",
    val overrideSkyToGray: Boolean = false,
    val pauseVoxelizer: Boolean = false,
    val overrideQualityLevel: String = "",
    val minGrassDistance: String = "",
    val maxGrassDistance: String = "",
    val lod: String = "",
    val lod12: String = "",
    val lod23: String = "",
    val lod34: String = "",
    val grassMovementReduced: String = ""
)