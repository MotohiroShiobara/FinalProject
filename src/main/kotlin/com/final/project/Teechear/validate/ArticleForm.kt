package com.final.project.Teechear.validate

import javax.validation.constraints.NotBlank

data class ArticleForm (
    @get:NotBlank val title: String = "",
    val markdownText: String = ""
)