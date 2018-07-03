package com.final.project.Teechear.validate

import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size

data class RegisterForm (
    @NotBlank(message = "このフィールドを入力してください")
    @Size(min = 4, max = 30)
    var accountName: String = "",

    @NotBlank(message = "このフィールドを入力してください")
    @Email(message = "メールアドレス")
    var email: String = "",

    @NotBlank(message = "このフィールドを入力してください")
    @Size(min = 8, max = 32, message = "8文字以上32文字以内で入力してください")
    var password: String = ""
)