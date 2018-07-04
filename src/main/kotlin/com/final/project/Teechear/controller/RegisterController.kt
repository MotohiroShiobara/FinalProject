package com.final.project.Teechear.controller

import com.final.project.Teechear.domain.User
import com.final.project.Teechear.mapper.UserMapper
import com.final.project.Teechear.validate.RegisterForm
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.BindingResult
import org.springframework.validation.FieldError
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import java.security.Principal

@Controller
class RegisterController(private val userMapper: UserMapper) {
    @GetMapping("", "/signup")
    fun register(model: Model, principal: Principal?): String {
        if (principal is Principal) {
            return "redirect:/trend"
        }
        val registerForm = RegisterForm()
        model.addAttribute("registerForm", registerForm)
        return "register"
    }

    @PostMapping("", "/signup")
    fun userRegister(
            @Validated registerForm: RegisterForm,
            bindingResult: BindingResult): String {
        if (userMapper.findByEmail(registerForm.email) is User) {
            bindingResult.addError(FieldError("uniq exception", "email", "メールアドレスはすでに登録済みです"))
        }

        if (userMapper.findByAccountName(registerForm.accountName) is User) {
            bindingResult.addError(FieldError("uniq exception", "accountName", "アカウント名はすでに登録済みです"))
        }

        if (bindingResult.hasErrors()) {
            return "register"
        }

        val user = User(
                registerForm.accountName,
                registerForm.email,
                BCryptPasswordEncoder().encode(registerForm.password))
        userMapper.insert(user)
        return "redirect:/trend"
    }
}