package com.final.project.Teechear.domain

data class Comment(val userId: Int? = null,
                   val articleId: Int? = null,
                   val markdownText: String? = null,
                   val id: Int? = null,
                   val userAccountName: String? = null)
