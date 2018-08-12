package com.final.project.teechear.controller

import com.final.project.teechear.service.TrendService
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import java.security.Principal

@Controller
class TopController(
        private val trendService: TrendService
) {

    @GetMapping("/trend")
    fun trend(model: Model, principal: Principal, @RequestParam("type") type: String?): String {

        if (type is String && type == "lesson") {
            model.addAttribute("type", "lesson")
            val lessonList = trendService.lessonList()
            model.addAttribute("lessonList", lessonList)
        } else {
            model.addAttribute("type", "article")
            val articleList = trendService.articleList()
            model.addAttribute("articleList", articleList)
        }

        return "top/trend"
    }
}