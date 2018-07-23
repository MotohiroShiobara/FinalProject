package com.final.project.Teechear.service

import com.final.project.Teechear.mapper.UserMapper
import com.final.project.Teechear.form.RegisterForm
import org.springframework.stereotype.Service
import org.springframework.validation.BindingResult
import org.springframework.validation.FieldError

@Service
class UserRegisterService(private val userMapper: UserMapper) {

    fun validation(registerForm: RegisterForm, bindingResult: BindingResult): BindingResult {
        if (userMapper.findByEmail(registerForm.email) != null) {
            bindingResult.addError(FieldError("uniq exception", "email", "メールアドレスはすでに登録済みです"))
        }

        if (userMapper.findByAccountName(registerForm.accountName) != null) {
            bindingResult.addError(FieldError("uniq exception", "accountName", "アカウント名はすでに登録済みです"))
        }

        return bindingResult
    }
}