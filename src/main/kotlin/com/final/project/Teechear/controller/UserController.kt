package com.final.project.Teechear.controller

import com.final.project.Teechear.domain.User
import com.final.project.Teechear.mapper.ArticleMapper
import com.final.project.Teechear.mapper.UserMapper
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import java.security.Principal

@Controller
@RequestMapping("/user")
class UserController(private val userMapper: UserMapper, private val articleMapper: ArticleMapper) {

    @GetMapping("/{userId}")
    fun show(@PathVariable("userId") userId: Int, model: Model): String {
        val user = userMapper.select(userId)
        val articleList = articleMapper.selectByUserId(userId)
        val contribution = articleList.sumBy { it.likeCount ?: 0 }

        if (user is User) {
            model.addAttribute("user", user)
            model.addAttribute("articleList", articleList)
            model.addAttribute("contribution", contribution)
            return "user/show"
        }

        return "error/404.html"
    }

    @GetMapping("/{userId}/edit")
    fun edit(@PathVariable("userId") userId: Int, model: Model, principal: Principal): String {
        val currentUser = userMapper.findByEmailOrName(principal.name)
        if (userId != currentUser.id) {
            return "redirect:/trend"
        }

        model.addAttribute("user", currentUser)
        return "user/edit"
    }
}