package com.final.project.Teechear.service

import com.final.project.Teechear.domain.TrendArticle
import com.final.project.Teechear.domain.TrendLesson
import com.final.project.Teechear.domainConverter.TrendArticleDomainConverter
import com.final.project.Teechear.domainConverter.TrendLessonDomainConverter
import com.final.project.Teechear.mapper.ArticleMapper
import com.final.project.Teechear.mapper.LessonMapper
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