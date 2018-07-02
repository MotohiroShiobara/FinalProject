package com.final.project.Qiita.controller

import com.final.project.Qiita.mapper.UserMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import java.security.Principal

@Controller
class LoginController @Autowired constructor(private val userMapper: UserMapper) {

    @GetMapping("/login")
    fun login(principal: Principal?): String {
        if (principal is Principal) {
            return "redirect:/trend"
        }
        
        return "login"
    }
}