package com.final.project.Qiita.domain

import java.time.LocalDateTime

data class Article(
        val userId: Int,
        val title: String,
        val releasedAt: LocalDateTime,
        val markdownText: String = "",
        val id: Int = 0
        )