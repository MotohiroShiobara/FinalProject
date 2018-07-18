package com.final.project.Teechear.validate

import java.time.LocalDateTime
import javax.validation.constraints.NotBlank

data class LessonNewForm(
        @get:NotBlank var title: String? = null,
        var price: Int? = null,
        var eventDateTime: LocalDateTime? = null,
        var description: String? = null,
        var emailAddress: String? = null,
        var imageUrl: String? = null
)
