package com.final.project.Teechear.service

import com.final.project.Teechear.domain.Lesson
import com.final.project.Teechear.domain.User
import com.final.project.Teechear.entity.LessonEntity
import com.final.project.Teechear.entity.UserApplyLessonEntity
import com.final.project.Teechear.mapper.LessonMapper
import com.final.project.Teechear.mapper.UserApplyLessonMapper
import com.final.project.Teechear.validate.LessonNewForm
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.util.*

@Service
class LessonService(
        private val dateTimeService: DateTimeService,
        private val lessonMapper: LessonMapper,
        private val userService: UserService,
        private val userApplyLessonMapper: UserApplyLessonMapper) {

    fun createByForm(form: LessonNewForm, userId: Int, imageUrl: String): Int {
        val eventDateTime = form.eventDateTime
        if (eventDateTime is LocalDateTime) {
            val convertedEventDateTime = dateTimeService.toDate(eventDateTime)
            val lessonEntity = LessonEntity(
                    form.title,
                    convertedEventDateTime,
                    form.price,
                    form.description,
                    imageUrl,
                    form.emailAddress,
                    userId,
                    true)
            lessonMapper.insert(lessonEntity)
            if (lessonEntity.id is Int) {
                return lessonEntity.id
            } else {
                throw LessonServiceException("作成に失敗しました")
            }
        } else {
            throw LessonServiceException("LocalDateTimeをDateに変換できませんでした")
        }
    }

    fun select(id: Int): Lesson {
        val lessonEntity = lessonMapper.select(id)
        return toDomain(lessonEntity)
    }

    fun selectByOwnerId(ownerId: Int): List<Lesson> {
        return lessonMapper.selectByOwnerId(ownerId).map { toDomain(it) }
    }

    fun apply(id: Int, userId: Int) {
        val userApplyLessonEntity = UserApplyLessonEntity(id, userId, Date())
        userApplyLessonMapper.insert(userApplyLessonEntity)
    }

    private fun toDomain(lessonEntity: LessonEntity?): Lesson
    {
        if (lessonEntity is LessonEntity) {
            if (lessonEntity.id is Int && lessonEntity.title is String && lessonEntity.eventDatetime is Date && lessonEntity.price is Int && lessonEntity.description is String && lessonEntity.emailAddress is String) {
                val imageUrl = if (lessonEntity.imageUrl is String) lessonEntity.imageUrl else "https://1.bp.blogspot.com/-Iv3bczeEefY/WxvJvlRTqEI/AAAAAAABMjc/9Rw8cVYk4B8P8_bcvXoA6gpLuByjtsPdQCLcBGAs/s400/computer_school_boy.png"
                if (lessonEntity.ownerId is Int) {
                    val user: User = userService.select(lessonEntity.ownerId)

                    return Lesson(lessonEntity.id, lessonEntity.title, lessonEntity.eventDatetime, lessonEntity.price, lessonEntity.description, lessonEntity.emailAddress, imageUrl, user.accountName, lessonEntity.ownerId, user.iconImageUrl)
                }
            }

            throw LessonServiceException("必要なカラムにnullが含まれています")
        }

        throw LessonServiceException("lessonが見つかりませんでした")
    }

    class LessonServiceException(s: String) : Exception()
    // TODO kotlinでのexceptionクラスの作り方に直す
}