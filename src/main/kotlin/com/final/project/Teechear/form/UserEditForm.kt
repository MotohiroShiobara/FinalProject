package com.final.project.Teechear.form

import org.springframework.web.multipart.MultipartFile
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Pattern
import javax.validation.constraints.Size

data class UserEditForm(
        @get:NotBlank(message = "アカウント名が空です")
        @get:Size(min = 4, max = 30, message = "アカウント名は最大4文字以上、30文字以内です")
        @get:Pattern(regexp = "^[a-zA-Z0-9\\-]+$", message = "ユーザ名は半角英数字及びハイフンのみ利用可能です")
        var accountName: String?,

        @get:Size(max = 1000, message = "1000文字以上入力することはできません")
        var profile: String?,

        var iconImageUrl: MultipartFile? = null
)