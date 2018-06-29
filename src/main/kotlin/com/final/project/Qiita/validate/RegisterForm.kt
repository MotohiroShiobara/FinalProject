package com.final.project.Qiita.validate

import javax.validation.constraints.NotBlank
import javax.validation.constraints.Email
import javax.validation.constraints.Size

class RegisterForm {

    @NotBlank
    @Size(min = 4, max = 30)
    var accountName: String = ""

    @NotBlank
    @Email
    var email: String = ""

    @NotBlank
    @Size(min = 8, max = 32)
    var password: String = ""
}