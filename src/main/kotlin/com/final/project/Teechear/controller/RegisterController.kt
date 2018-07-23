package com.final.project.Teechear.controller

import com.final.project.Teechear.entity.UserEntity
import com.final.project.Teechear.mapper.UserMapper
import com.final.project.Teechear.service.UserRegisterService
import com.final.project.Teechear.form.RegisterForm
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.BindingResult
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import java.security.Principal

@Controller
class RegisterController(private val userMapper: UserMapper, private val userRegisterService: UserRegisterService) {

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
        if (userRegisterService.validation(registerForm, bindingResult).hasErrors()) {
            return "register"
        }

        val user = UserEntity(
                registerForm.accountName,
                registerForm.email,
                BCryptPasswordEncoder().encode(registerForm.password))
        userMapper.insert(user)
        return "redirect:/trend"
    }
}