package com.final.project.Teechear.controller

import com.final.project.Teechear.mapper.UserMapper
import com.final.project.Teechear.service.ArticleService
import com.final.project.Teechear.service.LessonService
import com.final.project.Teechear.service.TrendService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import java.security.Principal

@Controller
class TopController(
        private val articleService: ArticleService,
        private val trendService: TrendService
) {
    @Autowired
    private val userMapper: UserMapper? = null

    @GetMapping("/trend")
    fun trend(model: Model, principal: Principal, @RequestParam("type") type: String?): String {

        if (type is String && type == "lesson") {
            model.addAttribute("type", "lesson")
            val lessonList = trendService.lessonList()
            model.addAttribute("lessonList", lessonList)
        } else {
            model.addAttribute("type", "article")
            val articleList = articleService.trendArticleList()
            model.addAttribute("articleList", articleList)
        }

        return "top/trend"
    }
}