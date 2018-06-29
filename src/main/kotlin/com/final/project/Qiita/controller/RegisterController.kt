package com.final.project.Qiita.controller

import com.final.project.Qiita.domain.User
import com.final.project.Qiita.mapper.UserMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.factory.PasswordEncoderFactories
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam

@Controller
class RegisterController @Autowired constructor(private val userMapper: UserMapper) {

    @GetMapping("", "/signup")
    fun register(): String {
        return "register"
    }

    @PostMapping("", "/signup")
    fun userRegister(
            @RequestParam(value = "name", required = true) name: String,
            @RequestParam(value = "email", required = true) email: String,
            @RequestParam(value = "pass", required = true) pass: String): String {

        val user = User(name, email, BCryptPasswordEncoder().encode(pass))
        userMapper.insert(user)
        return "redirect:/login"
    }
}