package com.final.project.Teechear.domain

import java.util.*

data class Article(
        val title: String? = null,
        val userId: Int? = null,
        val releasedAt: Date? = null,
        val markdownText: String? = null,
        val id: Int? = null,
        val userAccountName: String? = null,
        val likeCount: Int? = null)