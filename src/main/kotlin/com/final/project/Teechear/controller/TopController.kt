package com.final.project.Teechear.controller

import com.final.project.Teechear.mapper.ArticleMapper
import com.final.project.Teechear.mapper.UserMapper
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import java.nio.file.attribute.GroupPrincipal
import java.security.Principal

@Controller
class TopController(private val articleMapper: ArticleMapper, private val userMapper: UserMapper) {

    @GetMapping("/trend")
    fun trend(model: Model, principal: Principal): String {
        model.addAttribute("articleList", articleMapper.trend())
        model.addAttribute("currentUserId", userMapper.findByEmailOrName(principal.name)?.id)
        return "top/trend"
    }
}