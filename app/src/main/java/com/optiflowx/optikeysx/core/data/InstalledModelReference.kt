package com.optiflowx.optikeysx.core.data

import kotlinx.serialization.Serializable

@Serializable
data class InstalledModelReference(val path: String, val name: String, val type: ModelType)