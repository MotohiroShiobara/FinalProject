package com.final.project.Teechear.repository

import com.final.project.Teechear.mapper.UserApplyLessonMapper
import org.springframework.stereotype.Component

@Component
class UserApplyLessonRepository(private val userApplyLessonMapper: UserApplyLessonMapper) {

    fun hasParticipant(lessonId: Int): Boolean {
        return userApplyLessonMapper.selectByLessonIds(listOf(lessonId)).count() > 0
    }
}