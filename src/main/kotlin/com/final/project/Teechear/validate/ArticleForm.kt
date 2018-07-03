package com.final.project.Teechear.validate

import javax.validation.constraints.NotBlank

data class ArticleForm (
    @NotBlank var title: String = "",
    var markdownText: String = ""
)