package com.final.project.Teechear.service

import com.final.project.Teechear.validate.LessonNewForm
import org.springframework.stereotype.Service

@Service
class LessonService {

    fun createByForm(lessonNewForm: LessonNewForm) {
        println(lessonNewForm)
    }
}