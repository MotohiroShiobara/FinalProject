package com.final.project.Teechear.validate

import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size

data class ArticleForm (
    @get:NotBlank(message = "必須項目です")
    val title: String = "",
    @get:NotBlank(message = "必須項目です")
    @get:Size(max = 10000, message = "文字数が多すぎます。複数回に分けて投稿してください")
    val markdownText: String = ""
)