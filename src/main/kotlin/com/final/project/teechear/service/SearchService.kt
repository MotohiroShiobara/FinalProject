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

    fun searchByLesson(query: String): List<SearchResultLesson> {
        val escapeQuery = EscapeStringConverter.searchQuery(query)
        return lessonMapper.search(escapeQuery).map { searchResultLessonDomainConverter.toDomain(it) }
    }

    fun searchResultCountByArticle(query: String): Int {
        val escapeQuery = EscapeStringConverter.searchQuery(query)
        return articleMapper.searchCount(escapeQuery)
    }

    fun paginateSearchByArticle(query: String, paginate: PagiNate): List<SearchResultArticle> {
        val range = pagination.obtainRange(paginate)
        val escapeQuery = EscapeStringConverter.searchQuery(query)
        return articleMapper
                .searchByPaginate(query = escapeQuery, offset = range.offset, limit = (range.to - range.offset))
                .map { searchResultArticleDomainConverter.toDomain(it) }
    }
}
