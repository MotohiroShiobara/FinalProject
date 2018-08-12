package com.final.project.teechear.controller

import com.final.project.teechear.domain.Article
import com.final.project.teechear.domain.Lesson
import com.final.project.teechear.domain.PagiNate
import com.final.project.teechear.exception.PageNotFoundException
import com.final.project.teechear.service.PagiNationService
import com.final.project.teechear.service.SearchService
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam

@Controller
@RequestMapping("/search")
class SearchController(
        private val pagiNationService: PagiNationService,
        private val searchService: SearchService
) {

    @GetMapping("")
    fun search(
            model: Model,
            @RequestParam(value = "q") query: String,
            @RequestParam(value = "pageCount") pageCount: Int?,
            @RequestParam("type") type: String?): String {

        if (query.isNotEmpty()) {
            if (type is String && type == "lesson") {
                val lessonList = searchService.searchByLesson(query)
                model.addAttribute("lessonList", lessonList)
                model.addAttribute("type", "lesson")
            } else {
                val searchResultCount = searchService.searchResultCountByArticle(query)
                val paginate = try {
                    obtainPaginate(pageCount, searchResultCount)
                } catch (e: PageNotFoundException) {
                    return "error/404.html"
                }
                val articleList = searchService.paginateSearchByArticle(query, paginate)

                model.addAttribute("articleList", articleList)
                model.addAttribute("page", paginate)
                model.addAttribute("type", "article")
            }
        } else {
            model.addAttribute("lessonList", emptyList<Lesson>())
            model.addAttribute("articleList", emptyList<Article>())
            model.addAttribute("type", if (type == "lesson") "lesson" else "article")
        }

        model.addAttribute("query", query)
        return "search/result"
    }

    private fun obtainPaginate(pageCount: Int?, searchResultCount: Int): PagiNate {
        return pagiNationService.obtainPaginate(
                nullableCurrentPage = pageCount,
                resultCount = searchResultCount,
                perPageSize = 20)
    }
}