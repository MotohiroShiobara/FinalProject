package com.final.project.Qiita.controller

import com.final.project.Qiita.mapper.UserMapper
import com.final.project.Qiita.validate.ArticleForm
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.AbstractBindingResult
import org.springframework.validation.BindingResult
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam

@Controller
@RequestMapping("/article")
class ArticleController @Autowired constructor(private val userMapper: UserMapper) {

    @GetMapping("/new")
    fun new(model: Model): String {
        model.addAttribute("articleForm", ArticleForm())
        return "article/new"
    }

    @PostMapping("", "")
    fun create(
            @Validated articleForm: ArticleForm,
            bindingResult: BindingResult,
            @RequestParam(value = "title", required = true) title: String,
            @RequestParam(value = "markdownText", required = true) markdownText: String
    ): String {
        return "redirect:/article/"
    }

    @GetMapping("/{articleId}")
    fun show(@PathVariable("articleId") articleId: Int): String {
        println(articleId)
        return "article/show"
    }
}