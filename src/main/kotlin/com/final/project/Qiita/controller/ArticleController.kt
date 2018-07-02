package com.final.project.Qiita.controller

import com.final.project.Qiita.domain.Article
import com.final.project.Qiita.mapper.ArticleMapper
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
import java.security.Principal
import java.time.LocalDateTime

@Controller
@RequestMapping("/article")
class ArticleController @Autowired constructor(private val userMapper: UserMapper, private val articleMapper: ArticleMapper) {

    @GetMapping("/new")
    fun new(model: Model): String {
        model.addAttribute("articleForm", ArticleForm())
        return "article/new"
    }

    @PostMapping("", "")
    fun create(
            principal: Principal,
            @Validated articleForm: ArticleForm,
            bindingResult: BindingResult
    ): String {
        val currentUser = userMapper.findByEmailOrName(principal.name)
        val nowDateTime = LocalDateTime.now()
        if (nowDateTime is LocalDateTime) {
            val article = Article(currentUser.id, articleForm.title, nowDateTime, articleForm.markdownText)
            articleMapper.insert(article)
            return "redirect:/article"
        }

        return "article/new"
    }

    @GetMapping("/{articleId}")
    fun show(@PathVariable("articleId") articleId: Int): String {
        println(articleId)
        return "article/show"
    }
}