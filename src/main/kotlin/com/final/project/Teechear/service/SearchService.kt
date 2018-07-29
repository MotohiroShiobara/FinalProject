package com.final.project.Teechear.service

import com.final.project.Teechear.domain.SearchResultArticle
import com.final.project.Teechear.domain.SearchResultLesson
import com.final.project.Teechear.domainConverter.SearchResultArticleDomainConverter
import com.final.project.Teechear.domainConverter.SearchResultLessonDomainConverter
import com.final.project.Teechear.mapper.ArticleMapper
import com.final.project.Teechear.mapper.LessonMapper
import com.final.project.Teechear.util.EscapeStringConverter
import org.springframework.stereotype.Service

@Service
class SearchService(
        private val lessonMapper: LessonMapper,
        private val articleMapper: ArticleMapper,
        private val searchResultLessonDomainConverter: SearchResultLessonDomainConverter,
        private val searchResultArticleDomainConverter: SearchResultArticleDomainConverter) {

    fun searchByLesson(query: String): List<SearchResultLesson> {
        val escapeQuery = EscapeStringConverter.searchQuery(query)
        return lessonMapper.search(escapeQuery).map { searchResultLessonDomainConverter.toDomain(it) }
    }

    fun searchByArticle(query: String): List<SearchResultArticle> {
        val escapeQuery = EscapeStringConverter.searchQuery(query)
        return articleMapper.search(escapeQuery).map { searchResultArticleDomainConverter.toDomain(it) }
    }
}