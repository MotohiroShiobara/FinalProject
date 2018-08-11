package com.final.project.Teechear.service

import com.final.project.Teechear.domain.PagiNate
import com.final.project.Teechear.domain.SearchResultArticle
import com.final.project.Teechear.domain.SearchResultLesson
import com.final.project.Teechear.domainConverter.SearchResultArticleDomainConverter
import com.final.project.Teechear.domainConverter.SearchResultLessonDomainConverter
import com.final.project.Teechear.mapper.ArticleMapper
import com.final.project.Teechear.mapper.LessonMapper
import com.final.project.Teechear.util.EscapeStringConverter
import com.final.project.Teechear.util.Pagination
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

    fun searchByArticle(query: String): List<SearchResultArticle> {
        val escapeQuery = EscapeStringConverter.searchQuery(query)
        return articleMapper.search(escapeQuery).map {searchResultArticleDomainConverter.toDomain(it) }
    }

    // TODO mapperにintを返すようなものを実装する
    fun searchResultCountByArticle(query: String): Int {
        val escapeQuery = EscapeStringConverter.searchQuery(query)
        return articleMapper.search(escapeQuery).count()
    }

    fun paginateSearchByArticle(query: String, paginate: PagiNate): List<SearchResultArticle> {
        val range = pagination.obtainRange(paginate)
        return searchByArticle(query).subList(range.offset, range.to)
    }
}
