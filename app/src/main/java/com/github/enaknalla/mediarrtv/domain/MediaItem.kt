package com.github.enaknalla.mediarrtv.domain

import kotlinx.serialization.Serializable

@Serializable
data class MediaItem(
    val type: Int,
    val id: Int,
    val title: String,
    val subtitle: String,
    val poster: String? = null,
    var progress: Int = 0,
    val lastWatchedAt: String? = null,
    val info1: String? = null,
    val info2: String? = null,
    val banner: String,
    val description: String? = null,
    val torrentBody: TorrentBody? = null,
    val episodeCount: Int = 0,
    val extras: List<MediaList> = emptyList(),
    val showId: Int? = null,
    val season: Int? = null,
    val episode: Int? = null,
    val airDate: String? = null
)
