package com.optiflowx.optikeysx.core.model

import androidx.compose.runtime.Immutable

@Immutable
data class Key (val id: String,val value: String, val accents: List<String> = listOf())