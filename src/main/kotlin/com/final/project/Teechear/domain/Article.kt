package com.final.project.Teechear.domain

import java.util.*

data class Article(
        val id: Int,
        val title: String,
        val releasedAt: Date,
        val userId: Int,
        val userName: String,
        val markdownText: String,
        val likeCount: Int,
        val iconImageUrl: String)