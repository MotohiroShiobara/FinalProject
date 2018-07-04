package com.final.project.Teechear.validate

import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size

data class UserEditForm(
        @NotBlank(message = "アカウント名が空です")
        @Size(min = 4, max = 30, message = "アカウント名は最大4文字以上、30文字以内です")
        var accountName: String?,
        var profile: String?
)