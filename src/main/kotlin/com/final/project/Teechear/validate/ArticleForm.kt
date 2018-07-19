package com.final.project.Teechear.validate

import javax.validation.constraints.NotBlank

data class ArticleForm (
    @get:NotBlank(message = "必須項目です")
    val title: String = "",
    @get:NotBlank(message = "必須項目です")
    val markdownText: String = ""
)