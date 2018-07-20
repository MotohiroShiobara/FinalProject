package com.final.project.Teechear.validate

import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size

data class ArticleForm (
    @get:NotBlank(message = "必須項目です")
    @get:Size(min = 1, max = 50, message = "1文字以上50文字以下で入力してください")
    val title: String = "",
    @get:NotBlank(message = "必須項目です")
    val markdownText: String = ""
)