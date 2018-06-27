package com.final.project.Qiita.controller

import com.final.project.Qiita.mapper.UserMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.servlet.ModelAndView

@Controller
//@RequestMapping("/login")
class LoginController @Autowired constructor(private val userMapper: UserMapper) {

    @RequestMapping("/")
    fun root(): String{
        return "index"
    }

    @RequestMapping("/index")
    fun index(): String{
        return "index"
    }

    @RequestMapping("/login/success")
    fun users(): String {
        return "login/success"
    }
}