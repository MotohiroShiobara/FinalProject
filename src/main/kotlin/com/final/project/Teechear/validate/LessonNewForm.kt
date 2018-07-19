package com.final.project.Teechear.validate

import org.springframework.format.annotation.DateTimeFormat
import org.springframework.web.multipart.MultipartFile
import java.time.LocalDateTime
import javax.validation.constraints.NotBlank

data class LessonNewForm(
        @get:NotBlank var title: String? = null,
        var price: Int? = null,
        @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm") var eventDateTime: LocalDateTime? = null,
        var description: String? = null,
        var emailAddress: String? = null,
        var multipartFile: MultipartFile? = null
)
