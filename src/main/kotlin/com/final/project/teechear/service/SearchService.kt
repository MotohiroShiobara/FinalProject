package com.final.project.teechear.service

import com.final.project.teechear.domain.PagiNate
import com.final.project.teechear.domain.SearchResultArticle
import com.final.project.teechear.domain.SearchResultLesson
import com.final.project.teechear.domainConverter.SearchResultArticleDomainConverter
import com.final.project.teechear.domainConverter.SearchResultLessonDomainConverter
import com.final.project.teechear.mapper.ArticleMapper
import com.final.project.teechear.mapper.LessonMapper
import com.final.project.teechear.util.EscapeStringConverter
import com.final.project.teechear.util.Pagination
import org.springframework.stereotype.Service

@Service
class SearchService(
        private val lessonMapper: LessonMapper,
        private val articleMapper: ArticleMapper,
        private val searchResultLessonDomainConverter: SearchResultLessonDomainConverter,
        private val searchResultArticleDomainConverter: SearchResultArticleDomainConverter,
        private val pagination: Pagination) {

    fun searchResultCountByArticle(query: String): Int {
        return searchCount(query = query, funcSearch = { escapeQuery -> articleMapper.searchCount(escapeQuery) })
    }

    fun paginateSearchByArticle(query: String, paginate: PagiNate): List<SearchResultArticle> {
        return paginateSearch(
                query = query,
                paginate = paginate,
                funcSearch = { escapeQuery, offset, limit -> articleMapper.searchByPaginate(escapeQuery, offset, limit) }
        ).map { searchResultArticleDomainConverter.toDomain(it) }
    }

    fun searchResultCountByLesson(query: String): Int {
        return searchCount(query = query, funcSearch = { escapeQuery -> lessonMapper.searchCount(escapeQuery) })
    }

    fun paginateSearchByLesson(query: String, paginate: PagiNate): List<SearchResultLesson> {
        return paginateSearch(
                query = query,
                paginate = paginate,
                funcSearch = { escapeQuery, offset, limit -> lessonMapper.searchByPaginate(escapeQuery, offset, limit) }
        ).map { searchResultLessonDomainConverter.toDomain(it) }
    }

    private fun <T> paginateSearch(query: String, paginate: PagiNate, funcSearch: (String, Int, Int) -> T): T {
        val range = pagination.obtainRange(paginate)
        val escapeQuery = EscapeStringConverter.searchQuery(query)
        return funcSearch(escapeQuery, range.offset, (range.to - range.offset))
    }

    private fun <Int> searchCount(query: String, funcSearch: (String) -> Int): Int {
        val escapeQuery = EscapeStringConverter.searchQuery(query)
        return funcSearch(escapeQuery)
    }
}
