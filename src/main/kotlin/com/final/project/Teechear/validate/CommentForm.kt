package com.final.project.Teechear.validate

import javax.validation.constraints.NotBlank

data class CommentForm (
        @get:NotBlank
        val text: String? = null,
        val userId: Int? = null,
        val articleId: Int? = null
)

