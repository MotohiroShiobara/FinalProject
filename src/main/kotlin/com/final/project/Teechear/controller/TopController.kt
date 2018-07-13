package com.final.project.Teechear.controller

import com.final.project.Teechear.service.ArticleService
import com.final.project.Teechear.service.UserService
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import java.security.Principal

@Controller
class TopController(
        private val articleService: ArticleService,
        private val userService: UserService
) {

    @GetMapping("/trend")
    fun trend(model: Model, principal: Principal): String {
        val articleList = articleService.trendArticleList()
        val currentUser = userService.currentUser(principal)
        model.addAttribute("articleList", articleList)

        return "top/trend"
    }
}