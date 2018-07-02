package com.final.project.Qiita.controller

import com.final.project.Qiita.domain.User
import com.final.project.Qiita.mapper.UserMapper
import com.final.project.Qiita.validate.RegisterForm
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.BindingResult
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import java.security.Principal

@Controller
class RegisterController @Autowired constructor(private val userMapper: UserMapper) {
    @GetMapping("", "/signup")
    fun register(model : Model, principal: Principal?): String {
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
            bindingResult: BindingResult,
            @RequestParam(value = "accountName", required = true) accountName: String,
            @RequestParam(value = "email", required = true) email: String,
            @RequestParam(value = "password", required = true) password: String): String {

        if (bindingResult.hasErrors()) {
            return "register"
        }

        val user = User(accountName, email, BCryptPasswordEncoder().encode(password))
        userMapper.insert(user)
        return "redirect:/login"
    }
}