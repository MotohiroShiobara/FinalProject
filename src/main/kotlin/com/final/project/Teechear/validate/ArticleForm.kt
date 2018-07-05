package com.final.project.Teechear.validate

import javax.validation.constraints.NotBlank

data class ArticleForm (
    @get:NotBlank var title: String = "",
    var markdownText: String = ""
)