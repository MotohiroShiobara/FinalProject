package com.final.project.Teechear.entity

import java.util.*

data class ArticleEntity(
        val title: String? = null,
        val userId: Int? = null,
        val releasedAt: Date? = null,
        val markdownText: String? = null,
        val id: Int? = null,
        val user: UserEntity? = null,
        val userAccountName: String? = null,
        val likeCount: Int? = null)