package com.final.project.Teechear.controller

import com.final.project.Teechear.mapper.UserMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import java.security.Principal

@Controller
class LoginController @Autowired constructor(private val userMapper: UserMapper) {

    @GetMapping("/login")
    fun login(principal: Principal?, @RequestParam(value = "error") error: String?, model: Model): String {
        model.addAttribute("hasError", !error.isNullOrEmpty())
        return "login"
    }
}