package com.final.project.Teechear.domainConverter

import com.final.project.Teechear.domain.SearchResultLesson
import com.final.project.Teechear.domain.TrendLesson
import com.final.project.Teechear.entity.LessonEntity
import com.final.project.Teechear.entity.UserEntity
import com.final.project.Teechear.mapper.UserMapper
import com.final.project.Teechear.service.LessonService
import org.springframework.stereotype.Component
import java.util.*

@Component
class SearchResultLessonDomainConverter(private val userMapper: UserMapper) {

    fun toDomain(lessonEntity: LessonEntity?): SearchResultLesson {
        if (lessonEntity is LessonEntity) {
            println("entitty first")
            if (lessonEntity.id is Int && lessonEntity.title is String && lessonEntity.eventDatetime is Date && lessonEntity.price is Int && lessonEntity.ownerId is Int) {
                println("entity")
                val imageUrl = if (lessonEntity.imageUrl is String && lessonEntity.imageUrl.isNotEmpty()) lessonEntity.imageUrl else "https://1.bp.blogspot.com/-Iv3bczeEefY/WxvJvlRTqEI/AAAAAAABMjc/9Rw8cVYk4B8P8_bcvXoA6gpLuByjtsPdQCLcBGAs/s400/computer_school_boy.png"
                val user: UserEntity? = userMapper.select(lessonEntity.ownerId)
                println(user)
                if (user is UserEntity && user.accountName is String) {
                    return SearchResultLesson(lessonEntity.id, lessonEntity.title, lessonEntity.eventDatetime, lessonEntity.price, imageUrl, user.accountName, lessonEntity.ownerId)
                }
            }

            throw LessonService.LessonNotFoundException("必要なカラムにnullが含まれています")
        }

        throw LessonService.LessonNotFoundException("lessonが見つかりませんでした")
    }
}