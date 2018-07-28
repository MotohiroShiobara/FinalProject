package com.final.project.Teechear.service

import com.final.project.Teechear.domain.TrendLesson
import com.final.project.Teechear.domainConverter.TrendLessonDomainConverter
import com.final.project.Teechear.mapper.LessonMapper
import org.springframework.stereotype.Service

@Service
class TrendService(private val lessonMapper: LessonMapper, private val trendLessonDomainConverter: TrendLessonDomainConverter ) {

    fun lessonList(): List<TrendLesson> {
        return lessonMapper.trend().map { trendLessonDomainConverter.toDomain(it) }
    }
}