package com.optiflowx.optikeysx.core.model

data class AppUserData(
    val name: String,
    val email: String,
    val password: String,
    val isPremium: Boolean = false,
    val isPaid: Boolean = false,
)
