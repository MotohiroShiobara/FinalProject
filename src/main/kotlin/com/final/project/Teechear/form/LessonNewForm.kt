package com.final.project.Teechear.form

import com.final.project.Teechear.validate.NotImage
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.web.multipart.MultipartFile
import javax.validation.constraints.*

data class LessonNewForm(
        @get:NotBlank(message = "この項目は必須項目です")
        var title: String? = null,

        @get:Min(0, message = "0円以上の金額で設定してください")
        @get:Max(10000, message = "設定できる料金は一万円以下です")
        @get:NotNull(message = "この項目は必須項目です")
        var price: Int? = null,

        @DateTimeFormat(pattern ="yyyy-MM-dd'T'HH:mm")
        @get:NotBlank(message = "この項目は必須項目です")
        var eventDatetime: String? = null,

        @get:NotBlank(message = "この項目は必須項目です")
        @get:Size(max = 10000, message = "10000文字以上入力することはできません")
        var description: String? = null,

        @get:Email(message = "正しいメールアドレスを入力してください")
        @get:NotBlank(message = "この項目は必須項目です")
        var emailAddress: String? = null,

        @NotImage()
        var multipartFile: MultipartFile? = null
)
