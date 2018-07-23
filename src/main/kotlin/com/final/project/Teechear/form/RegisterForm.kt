package com.final.project.Teechear.form

import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Pattern
import javax.validation.constraints.Size

data class RegisterForm (
    @get:NotBlank(message = "このフィールドを入力してください")
    @get:Size(min = 4, max = 30, message = "4文字以上30文字以内で入力してください")
    @get:Pattern(regexp = "^[a-zA-Z0-9\\-]+$", message = "ユーザ名は半角英数字及びハイフンのみ利用可能です")
    var accountName: String = "",

    @get:NotBlank(message = "このフィールドを入力してください")
    @get:Email(message = "正しいメールアドレス入力してください")
    var email: String = "",

    @get:NotBlank(message = "このフィールドを入力してください")
    @get:Size(min = 8, max = 32, message = "8文字以上32文字以内で入力してください")
    var password: String = ""
)