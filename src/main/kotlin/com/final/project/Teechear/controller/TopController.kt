package com.final.project.Teechear.controller

import com.final.project.Teechear.mapper.ArticleMapper
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping

@Controller
class TopController(private val articleMapper: ArticleMapper) {

    @GetMapping("/trend")
    fun trend(model: Model): String {
        println(articleMapper.trend())
        model.addAttribute("articleList", articleMapper.trend())
        return "top/trend"
    }
}