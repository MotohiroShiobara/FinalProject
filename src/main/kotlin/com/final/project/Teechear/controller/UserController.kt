package com.final.project.Teechear.controller

import com.final.project.Teechear.entity.UserEntity
import com.final.project.Teechear.mapper.ArticleMapper
import com.final.project.Teechear.mapper.UserMapper
import com.final.project.Teechear.service.ArticleService
import com.final.project.Teechear.validate.UserEditForm
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.BindingResult
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import java.security.Principal

@Controller
@RequestMapping("/user")
class UserController(private val userMapper: UserMapper,
                     private val articleMapper: ArticleMapper,
                     private val articleService: ArticleService) {

    @GetMapping("/{userId}")
    fun show(
            @PathVariable("userId") userId: Int,
            model: Model,
            principal: Principal,
            @RequestParam("page") pageCount: Int?): String {
        val user = userMapper.select(userId)
        val currentUser = userMapper.findByEmailOrName(principal.name)
        val articleList = articleService.userArticleList(userId)
        val contribution = articleList.sumBy { it.likeCount ?: 0 }

        if (user is UserEntity) {
            model.addAttribute("currentUserId", currentUser?.id)
            model.addAttribute("user", user)
            model.addAttribute("articleList", articleList)
            model.addAttribute("contribution", contribution)
            model.addAttribute("pageCount", pageCount!!)
            model.addAttribute("pageList", (1..3).map{it})
            return "user/show"
        }

        return "error/404.html"
    }

    @GetMapping("/mypage")
    fun mypage(principal: Principal): String {
        val currentUser = userMapper.findByEmailOrName(principal.name)
        return "redirect:/user/" + currentUser?.id
    }

    @GetMapping("/{userId}/edit")
    fun edit(@PathVariable("userId") userId: Int, model: Model, principal: Principal): String {
        val currentUser = obtainCurrentUser(principal.name)
        if (userId != currentUser?.id) {
            return "redirect:/trend"
        }

        model.addAttribute("userEditForm", UserEditForm(currentUser?.accountName, currentUser?.profile))
        return "user/edit"
    }

    @PostMapping("")
    fun update(@Validated userEditForm: UserEditForm, bindingResult: BindingResult, principal: Principal): String {
        if (bindingResult.hasErrors()) {
            return "user/edit"
        }

        val currentUser = obtainCurrentUser(principal.name)
        val copyCurrentUser = currentUser?.copy(accountName = userEditForm.accountName, profile = userEditForm.profile)

        userMapper.update(copyCurrentUser!!)
        return "redirect:user/${currentUser.id}"
    }

    private fun obtainCurrentUser(accountName: String): UserEntity? {
        return userMapper.findByEmailOrName(accountName)
    }
}

