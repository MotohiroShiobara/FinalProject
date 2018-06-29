package com.final.project.Qiita.controller

import com.final.project.Qiita.mapper.UserMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/article")
class ArticleController @Autowired constructor(private val userMapper: UserMapper) {

    @GetMapping("/new")
    fun new(): String {
        return "article/new"
    }
}