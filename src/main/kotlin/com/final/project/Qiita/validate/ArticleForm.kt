package com.final.project.Qiita.validate

import javax.validation.constraints.NotBlank

class ArticleForm {
    @NotBlank
    var title: String = ""

    var markdownText: String = ""
}