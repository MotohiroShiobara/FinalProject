package com.final.project.teechear.domainConverter

import com.final.project.teechear.domain.TrendLesson
import com.final.project.teechear.entity.LessonEntity
import com.final.project.teechear.entity.UserEntity
import com.final.project.teechear.mapper.UserMapper
import com.final.project.teechear.service.LessonService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.util.*

@Component
class TrendLessonDomainConverter {

    @Autowired
    private val userMapper: UserMapper? = null

    fun toDomain(lessonEntity: LessonEntity?): TrendLesson {
        if (lessonEntity is LessonEntity) {
            if (lessonEntity.id is Int && lessonEntity.title is String && lessonEntity.eventDatetime is Date && lessonEntity.price is Int && lessonEntity.ownerId is Int) {
                val imageUrl = if (lessonEntity.imageUrl is String && lessonEntity.imageUrl.isNotEmpty()) lessonEntity.imageUrl else "https://1.bp.blogspot.com/-Iv3bczeEefY/WxvJvlRTqEI/AAAAAAABMjc/9Rw8cVYk4B8P8_bcvXoA6gpLuByjtsPdQCLcBGAs/s400/computer_school_boy.png"
                val user: UserEntity? = userMapper?.select(lessonEntity.ownerId)
                if (user is UserEntity && user.accountName is String) {
                    return TrendLesson(lessonEntity.id, lessonEntity.title, lessonEntity.eventDatetime, lessonEntity.price, imageUrl, user.accountName, lessonEntity.ownerId)
                }
            }

            throw LessonService.LessonNotFoundException("必要なカラムにnullが含まれています")
        }

        throw LessonService.LessonNotFoundException("lessonが見つかりませんでした")
    }
}