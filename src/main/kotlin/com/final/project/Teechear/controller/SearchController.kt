package com.final.project.Teechear.controller

import com.final.project.Teechear.mapper.ArticleMapper
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam

@Controller
@RequestMapping("/search")
class SearchController(private val articleMapper: ArticleMapper) {

    @GetMapping("")
    fun search(model: Model, @RequestParam(value = "q") query: String): String {
        model.addAttribute("articleList", articleMapper.search(query))
        model.addAttribute("query", query)
        return "search/result"
    }
}