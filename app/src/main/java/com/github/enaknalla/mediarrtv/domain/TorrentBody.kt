package com.github.enaknalla.mediarrtv.domain

import kotlinx.serialization.Serializable

@Serializable
data class TorrentBody(
    val aliases: List<String>? = emptyList(),
    val title: String,
    val year: Int,
    val season: Int?,
    val episode: Int?,
)
