package com.final.project.Teechear.controller

import com.final.project.Teechear.domain.User
import com.final.project.Teechear.mapper.UserMapper
import com.final.project.Teechear.validate.RegisterForm
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.BindingResult
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
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
            bindingResult: BindingResult): String {
        if (bindingResult.hasErrors()) {
            return "register"
        }

        val user = User(
                registerForm.accountName,
                registerForm.email,
                BCryptPasswordEncoder().encode(registerForm.password))
        userMapper.insert(user)
        return "redirect:/login"
    }
}