package com.final.project.teechear.service

import com.final.project.teechear.domain.TrendArticle
import com.final.project.teechear.domain.TrendLesson
import com.final.project.teechear.domainConverter.TrendArticleDomainConverter
import com.final.project.teechear.domainConverter.TrendLessonDomainConverter
import com.final.project.teechear.mapper.ArticleMapper
import com.final.project.teechear.mapper.LessonMapper
import org.springframework.stereotype.Service

@Service
class TrendService(
        private val articleMapper: ArticleMapper,
        private val lessonMapper: LessonMapper,
        private val trendLessonDomainConverter: TrendLessonDomainConverter,
        private val trendArticleDomainConverter: TrendArticleDomainConverter) {

    fun lessonList(): List<TrendLesson> {
        return lessonMapper.trend().map { trendLessonDomainConverter.toDomain(it) }
    }

    fun articleList(): List<TrendArticle> {
        val articleEntityList = articleMapper.trend()
        return articleEntityList.map { trendArticleDomainConverter.toDomain(it) }
    }
}