package com.github.enaknalla.mediarrtv.domain

import kotlinx.serialization.Serializable

@Serializable
data class AuthFlow(
    val code: String,
    val expiresIn: Long,
    val interval: Long
)