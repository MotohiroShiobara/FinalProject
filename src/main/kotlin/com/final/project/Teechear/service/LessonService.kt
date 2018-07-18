package com.final.project.Teechear.service

import com.final.project.Teechear.validate.LessonNewForm
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class LessonService(
        private val dateTimeService: DateTimeService
) {

    fun createByForm(lessonNewForm: LessonNewForm) {
        val eventDateTime = lessonNewForm.eventDateTime
        if (eventDateTime is LocalDateTime) {
        }

    }

}