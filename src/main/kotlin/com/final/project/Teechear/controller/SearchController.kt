package com.final.project.Teechear.controller

import com.final.project.Teechear.domain.Article
import com.final.project.Teechear.domain.Lesson
import com.final.project.Teechear.service.PagiNationService
import com.final.project.Teechear.service.SearchService
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
    fun search(model: Model, @RequestParam(value = "q") query: String, @RequestParam(value = "pageCount") pageCount: Int?, @RequestParam("type") type: String?): String {
        if (query.isNotEmpty()) {
            if (type is String && type == "lesson") {
                val lessonList = searchService.searchByLesson(query)
                model.addAttribute("lessonList", lessonList)
                model.addAttribute("type", "lesson")
            } else {
                // TODO searchResultのcountを返すようなmapperを用意
                // TODO PaginatinoSearcHResultArticleを排除
                // TODO paginateを使って取得範囲を特定し、そのarticleを返すようなメソッドを実装する
                val searchResultCount = searchService.searchResultCountByArticle(query)
                val paginate = pagiNationService.obtainPaginate(
                        currentPage = pageCount ?: 0,
                        resultCount = searchResultCount,
                        perPageSize = 20)
                val articleList = searchService.paginateSearchByArticle(query, paginate)

                model.addAttribute("articleList", articleList)
                model.addAttribute("paginate", paginate)
                model.addAttribute("type", "article")
            }
        } else {
            model.addAttribute("lessonList", emptyList<Lesson>())
            model.addAttribute("articleList", emptyList<Article>())
            model.addAttribute("type", if (type == "lesson") "lesson" else "article" )
        }

        model.addAttribute("query", query)
        return "search/result"
    }
}