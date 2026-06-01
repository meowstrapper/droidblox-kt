package com.drake.droidblox.ui.view.views.fastflags.state

data class FFlagsScreenCallbacks(
    val applyFFlags: (Boolean) -> Unit = {},
    val forceMSAA: (String) -> Unit = {},
    val renderingMode: (String) -> Unit = {},
    val textureQuality: (String) -> Unit = {},
    val overrideSkyToGray: (Boolean) -> Unit = {},
    val pauseVoxelizer: (Boolean) -> Unit = {},
    val overrideQualityLevel: (String) -> Unit = {},
    val minGrassDistance: (String) -> Unit = {},
    val maxGrassDistance: (String) -> Unit = {},
    val lod: (String) -> Unit = {},
    val lod12: (String) -> Unit = {},
    val lod23: (String) -> Unit = {},
    val lod34: (String) -> Unit = {},
    val grassMovementReduced: (String) -> Unit = {}
)