package com.final.project.Teechear.service

import com.final.project.Teechear.domain.ParticipantUser
import com.final.project.Teechear.entity.UserApplyLessonEntity
import com.final.project.Teechear.mapper.UserApplyLessonMapper
import com.final.project.Teechear.repository.UserApplyLessonRepository
import org.springframework.stereotype.Service

@Service
class UserApplyLessonService(
        private val userApplyLessonMapper: UserApplyLessonMapper,
        private val lessonService: LessonService,
        private val userService: UserService,
        private val userApplyLessonRepository: UserApplyLessonRepository) {

    fun selectByLessonIds(lessonIds: List<Int>): List<ParticipantUser> {
        return userApplyLessonMapper.selectByLessonIds(lessonIds).map { toDomain(it) }
    }

    fun hasParticipant(lessonId: Int): Boolean {
        return userApplyLessonRepository.hasParticipant(lessonId)
    }

    private fun toDomain(userApplyLessonEntitiy: UserApplyLessonEntity?): ParticipantUser {
        if (userApplyLessonEntitiy is UserApplyLessonEntity) {
            val lesson = lessonService.select(userApplyLessonEntitiy.lessonId!!)
            val user = userService.select(userApplyLessonEntitiy.userId!!)
            return ParticipantUser(
                    user.id,
                    lesson.title,
                    userApplyLessonEntitiy.applyDatetime!!,
                    user.accountName,
                    user.iconImageUrl,
                    lesson.id)
        }

        throw UserApplyLessonNotFoundException("userApplyEntityがありません")
    }

    class UserApplyLessonNotFoundException(s: String) : Exception()
}