package com.final.project.Qiita.validate

import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size

class ArticleForm {
    @NotBlank
    var title: String = ""

    var markdownText: String = ""
}