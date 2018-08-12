package com.final.project.teechear.repository

import com.final.project.teechear.mapper.UserApplyLessonMapper
import org.springframework.stereotype.Component

@Component
class UserApplyLessonRepository(private val userApplyLessonMapper: UserApplyLessonMapper) {

    fun hasParticipant(lessonId: Int): Boolean {
        return userApplyLessonMapper.selectByLessonIds(listOf(lessonId)).count() > 0
    }
}