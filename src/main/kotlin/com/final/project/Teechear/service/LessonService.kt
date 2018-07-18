package com.final.project.Teechear.service

import com.final.project.Teechear.entity.LessonEntity
import com.final.project.Teechear.mapper.LessonMapper
import com.final.project.Teechear.validate.LessonNewForm
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class LessonService(
        private val dateTimeService: DateTimeService,
        private val lessonMapper: LessonMapper) {

    fun createByForm(form: LessonNewForm, userId: Int) {
        val eventDateTime = form.eventDateTime
        println(eventDateTime is LocalDateTime)
        if (eventDateTime is LocalDateTime) {
            val convertedEventDateTime = dateTimeService.toDate(eventDateTime)
            val lessonEntity = LessonEntity(
                    form.title,
                    convertedEventDateTime,
                    form.price,
                    form.description,
                    form.imageUrl,
                    form.emailAddress,
                    userId,
                    true)
            lessonMapper.insert(lessonEntity)
        } else {
            throw LessonServiceException("LocalDateTimeをDateに変換できませんでした")
        }
    }

    class LessonServiceException(s: String): Exception()
    // TODO kotlinでのexceptionクラスの作り方に直す
}