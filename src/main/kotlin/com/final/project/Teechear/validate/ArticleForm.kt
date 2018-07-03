package com.final.project.Teechear.validate

import javax.validation.constraints.NotBlank

class ArticleForm {
    @NotBlank
    var title: String = ""

    var markdownText: String = ""
}