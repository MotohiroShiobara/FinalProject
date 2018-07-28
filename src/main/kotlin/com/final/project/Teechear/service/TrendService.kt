package com.final.project.Teechear.service

import com.final.project.Teechear.domain.Lesson
import com.final.project.Teechear.domain.TrendLesson
import com.final.project.Teechear.domain.User
import com.final.project.Teechear.entity.LessonEntity
import com.final.project.Teechear.mapper.LessonMapper
import org.springframework.stereotype.Service
import java.util.*

@Service
class TrendService(private val lessonMapper: LessonMapper, private val userService: UserService ) {

    fun lessonList(): List<TrendLesson> {
        return lessonMapper.trend().map { toDomain(it) }
    }

    private fun toDomain(lessonEntity: LessonEntity?): TrendLesson
    {
        if (lessonEntity is LessonEntity) {
            if (lessonEntity.id is Int && lessonEntity.title is String && lessonEntity.eventDatetime is Date && lessonEntity.price is Int && lessonEntity.description is String && lessonEntity.emailAddress is String && lessonEntity.isOpen is Boolean) {
                val imageUrl = if (lessonEntity.imageUrl is String && lessonEntity.imageUrl.isNotEmpty()) lessonEntity.imageUrl else "https://1.bp.blogspot.com/-Iv3bczeEefY/WxvJvlRTqEI/AAAAAAABMjc/9Rw8cVYk4B8P8_bcvXoA6gpLuByjtsPdQCLcBGAs/s400/computer_school_boy.png"
                if (lessonEntity.ownerId is Int) {
                    val user: User = userService.select(lessonEntity.ownerId)

                    return TrendLesson(lessonEntity.id, lessonEntity.title, lessonEntity.eventDatetime, lessonEntity.price, imageUrl, user.accountName, lessonEntity.ownerId)
                }
            }

            throw LessonService.LessonServiceException("必要なカラムにnullが含まれています")
        }

        throw LessonService.LessonNotFoundException("lessonが見つかりませんでした")
    }
}