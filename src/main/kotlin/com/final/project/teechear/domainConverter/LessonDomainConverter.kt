package com.final.project.teechear.domainConverter

import com.final.project.teechear.domain.Lesson
import com.final.project.teechear.entity.LessonEntity
import com.final.project.teechear.exception.DomainArgumentException
import com.final.project.teechear.mapper.UserMapper
import org.springframework.stereotype.Component
import java.util.*

@Component
class LessonDomainConverter(private val userMapper: UserMapper) {

    fun toDomain(lessonEntity: LessonEntity?): Lesson? {
        if (lessonEntity !is LessonEntity) return null
        if (lessonEntity.id is Int
                && lessonEntity.title is String
                && lessonEntity.eventDatetime is Date
                && lessonEntity.price is Int
                && lessonEntity.description is String
                && lessonEntity.emailAddress is String
                && lessonEntity.isOpen is Boolean
                && lessonEntity.ownerId is Int) {

            val imageUrl = lessonEntity.imageUrl
                    ?: "https://1.bp.blogspot.com/-Iv3bczeEefY/WxvJvlRTqEI/AAAAAAABMjc/9Rw8cVYk4B8P8_bcvXoA6gpLuByjtsPdQCLcBGAs/s400/computer_school_boy.png"

            val user = userMapper.select(lessonEntity.ownerId) ?: throw DomainArgumentException("lessonEntity: $lessonEntity")
            val accountName = user.accountName ?: throw DomainArgumentException("user: $user")
            return Lesson(
                    lessonEntity.id,
                    lessonEntity.title,
                    lessonEntity.eventDatetime,
                    lessonEntity.price,
                    lessonEntity.description,
                    lessonEntity.emailAddress,
                    imageUrl,
                    accountName,
                    lessonEntity.ownerId,
                    user.iconImageUrl ?: "",
                    lessonEntity.isOpen,
                    lessonEntity.estimatedTime)
        }

        throw DomainArgumentException("lessonEntity: $lessonEntity")
    }
}
