package com.final.project.teechear.form

import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size

data class CommentForm (
        @get:NotBlank
        @get:Size(max = 13000)
        val text: String? = null,
        val userId: Int? = null,
        val articleId: Int? = null
)

