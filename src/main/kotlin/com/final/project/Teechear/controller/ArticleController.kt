package com.final.project.Teechear.controller

import com.final.project.Teechear.domain.Article
import com.final.project.Teechear.mapper.ArticleMapper
import com.final.project.Teechear.mapper.UserMapper
import com.final.project.Teechear.validate.ArticleForm
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.BindingResult
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import java.security.Principal
import java.time.LocalDateTime

@Controller
@RequestMapping("/article")
class ArticleController(private val userMapper: UserMapper, private val articleMapper: ArticleMapper) {

    @GetMapping("/new")
    fun new(model: Model): String {
        model.addAttribute("articleForm", ArticleForm())
        return "article/new"
    }

    @PostMapping("")
    fun create(
            principal: Principal,
            @Validated articleForm: ArticleForm,
            bindingResult: BindingResult
    ): String {
        val currentUser = userMapper.findByEmailOrName(principal.name)
        val nowDateTime = LocalDateTime.now()
        println("article Formの中身")
        println(articleForm.title)
        println(articleForm.markdownText)
        nowDateTime.toq
        val article = Article(articleForm.title, currentUser.id, nowDateTime, articleForm.markdownText)
        articleMapper.insert(article)
        return "redirect:/article/${article.id}"
    }

    @GetMapping("/{articleId}")
    fun show(@PathVariable("articleId") articleId: Int, model: Model): String {
        val article = articleMapper.find(articleId)
        val userId = article?.userId
        if (userId is Int) {

        }
        if (article is Article) {
            model.addAttribute("article", article)
            return "article/show"
        }

        return "error/404.html"
    }
}