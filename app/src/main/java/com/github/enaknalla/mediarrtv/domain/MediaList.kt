package com.github.enaknalla.mediarrtv.domain

import kotlinx.serialization.Serializable

@Serializable
data class MediaList(
    val title: String,
    val items: List<MediaItem>
)
