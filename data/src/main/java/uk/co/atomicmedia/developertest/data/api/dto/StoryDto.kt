package uk.co.atomicmedia.developertest.data.api.dto

import java.time.Instant

data class StoryDto(
    val id: String,
    val title: String,
    val author: String,
    val content: String,
    val publishedAt: Instant,
)