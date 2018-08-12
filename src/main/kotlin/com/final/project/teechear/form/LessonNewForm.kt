package com.final.project.teechear.form

import com.final.project.teechear.validate.NotImage
import org.springframework.web.multipart.MultipartFile
import javax.validation.constraints.*

data class LessonNewForm(
        @get:NotBlank(message = "この項目は必須項目です")
        @get:Size(max = 100, message = "1文字以上50文字以下で設定してください")
        var title: String? = null,

        @get:Min(0, message = "0円以上の金額で設定してください")
        @get:Max(10000, message = "設定できる料金は一万円以下です")
        @get:NotNull(message = "この項目は必須項目です")
        var price: Int? = null,

        @get:NotBlank(message = "日付を入力してください")
        var eventDate: String? = null,

        @get:NotBlank(message = "時間を入力してください")
        var eventTime: String? = null,

        @get:NotBlank(message = "この項目は必須項目です")
        @get:Size(max = 15000, message = "10000文字以上入力することはできません")
        var description: String? = null,

        @get:Email(message = "正しいメールアドレスを入力してください")
        @get:NotBlank(message = "この項目は必須項目です")
        var emailAddress: String? = null,

        @NotImage()
        var multipartFile: MultipartFile? = null,

        @get:Min(0, message = "0分以上に設定してください")
        @get:Max(3600, message = "3600分(24時間)以上に設定することはできません")
        var estimatedTime: Int? = null
)
