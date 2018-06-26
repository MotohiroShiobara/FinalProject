package com.final.project.Qiita.controller

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/user")
class UserController {

    @GetMapping("/", "")
    fun show(): String {
        return "login"
    }
}