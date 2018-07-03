package com.final.project.Teechear.domain

data class Article(
        val title: String? = null,
        val userId: Int? = null,
        val releasedAt: java.time.LocalDateTime? = null,
        val markdownText: String? = null,
        val id: Int? = null)
