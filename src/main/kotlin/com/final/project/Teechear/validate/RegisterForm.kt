package com.final.project.Teechear.validate

import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size

data class RegisterForm (
    @get:NotBlank(message = "このフィールドを入力してください")
    @get:Size(min = 4, max = 30, message = "4文字以上30文字以内で入力してください")
    var accountName: String = "",

    @get:NotBlank(message = "このフィールドを入力してください")
    @get:Email(message = "メールアドレス")
    var email: String = "",

    @get:NotBlank(message = "このフィールドを入力してください")
    @get:Size(min = 8, max = 32, message = "8文字以上32文字以内で入力してください")
    var password: String = ""
)