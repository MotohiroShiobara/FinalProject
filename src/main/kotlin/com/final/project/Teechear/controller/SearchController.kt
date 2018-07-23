package com.final.project.Teechear.controller

import com.final.project.Teechear.domain.Article
import com.final.project.Teechear.entity.ArticleEntity
import com.final.project.Teechear.mapper.ArticleMapper
import com.final.project.Teechear.service.ArticleService
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam

@Controller
@RequestMapping("/search")
class SearchController(
        private val articleService: ArticleService
) {

    @GetMapping("")
    fun search(model: Model, @RequestParam(value = "q") query: String): String {
        if (query.isNotEmpty()) {
            val articleList: List<Article> = articleService.search(query)
            model.addAttribute("articleList", articleList)
        } else {
            model.addAttribute("articleList", emptyList<ArticleEntity>())
        }

        model.addAttribute("query", query)
        return "search/result"
    }
}