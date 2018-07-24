package com.final.project.Teechear.controller

import com.final.project.Teechear.domain.Article
import com.final.project.Teechear.domain.Lesson
import com.final.project.Teechear.service.ArticleService
import com.final.project.Teechear.service.LessonService
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam

@Controller
@RequestMapping("/search")
class SearchController(
        private val articleService: ArticleService,
        private val lessonService: LessonService
) {

    @GetMapping("")
    fun search(model: Model, @RequestParam(value = "q") query: String, @RequestParam("type") type: String?): String {
        if (query.isNotEmpty()) {
            if (type is String && type == "lesson") {
                val lessonList = lessonService.search(query)
                model.addAttribute("lessonList", lessonList)
                model.addAttribute("type", "lesson")
            } else {
                val articleList: List<Article> = articleService.search(query)
                model.addAttribute("articleList", articleList)
                model.addAttribute("type", "article")
            }
        } else {
            model.addAttribute("lessonList", emptyList<Lesson>())
            model.addAttribute("articleList", emptyList<Article>())
            model.addAttribute("type", "article")
        }

        model.addAttribute("query", query)
        return "search/result"
    }
}