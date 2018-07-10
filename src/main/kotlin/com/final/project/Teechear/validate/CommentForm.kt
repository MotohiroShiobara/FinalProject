package com.final.project.Teechear.validate

import javax.validation.constraints.NotBlank


data class CommentForm (
        @get:NotBlank
        var text: String = ""
)

