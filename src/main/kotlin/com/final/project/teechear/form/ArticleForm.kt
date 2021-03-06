package com.final.project.teechear.form

import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size

data class ArticleForm (
    @get:NotBlank(message = "必須項目です")
    @get:Size(min = 1, max = 100, message = "1文字以上50文字以下で入力してください")
    val title: String = "",
    @get:NotBlank(message = "必須項目です")
    @get:Size(max = 15000, message = "文字数が多すぎます。複数回に分けて投稿してください")
    val markdownText: String = ""
)