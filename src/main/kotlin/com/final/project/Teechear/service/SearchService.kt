package com.final.project.Teechear.service

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

    // TODO searchResultのCountを返すためのSQLをMapperに定義する
    fun pageSearchByArticle(query: String, pageCount: Int, perPageCount: Int): List<SearchResultArticle> {
        val escapeQuery = EscapeStringConverter.searchQuery(query)
        val result = articleMapper.search(escapeQuery)
        val range: Pagination.Range = pagination.obtainRange(pageSize = result.count(), currentPage = pageCount, perPageSize = perPageCount)

        return result.subList(range.offset, range.to).map {searchResultArticleDomainConverter.toDomain(it) }
    }
}
