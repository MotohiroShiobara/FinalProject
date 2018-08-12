package com.final.project.teechear.entity

data class CommentEntity(val userId: Int? = null,
                         val articleId: Int? = null,
                         val markdownText: String? = null,
                         val id: Int? = null,
                         val userAccountName: String? = null)
