package com.final.project.Teechear.service

import com.final.project.Teechear.domain.Lesson
import com.final.project.Teechear.domain.User
import com.final.project.Teechear.entity.LessonEntity
import com.final.project.Teechear.entity.UserApplyLessonEntity
import com.final.project.Teechear.mapper.LessonMapper
import com.final.project.Teechear.mapper.UserApplyLessonMapper
import com.final.project.Teechear.validate.LessonNewForm
import org.springframework.stereotype.Service
import org.springframework.validation.BindingResult
import org.springframework.validation.FieldError
import java.time.LocalDateTime
import java.util.*

@Service
class LessonService(
        private val dateTimeService: DateTimeService,
        private val lessonMapper: LessonMapper,
        private val userService: UserService,
        private val userApplyLessonMapper: UserApplyLessonMapper) {

    fun createByForm(form: LessonNewForm, userId: Int, imageUrl: String): Int {
        val eventDateTime = form.eventDatetime
        if (eventDateTime is String) {
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

    fun validation(form: LessonNewForm, result: BindingResult): BindingResult {
        // eventDatetimeは未来でなければならない
        val eventDatetime = form.eventDatetime
        if (eventDatetime is String) {
            val date = dateTimeService.toDate(eventDatetime)
            if (date.before(Date()) || date.equals(Date())) {
                result.addError(FieldError("invalid datetime", "eventDatetime", "過去の開催日時を選択することはできません"))
            }
        }

        return result
    }

    fun select(id: Int): Lesson {
        val lessonEntity = lessonMapper.select(id)
        return toDomain(lessonEntity)
    }

    fun close(id: Int) {
        lessonMapper.close(id)
    }

    fun openByOwnerId(ownerId: Int): List<Lesson> {
        return lessonMapper.openByOwnerId(ownerId).map { toDomain(it) }
    }

    fun closeByOwnerId(ownerId: Int): List<Lesson> {
        return lessonMapper.closeByOwnerId(ownerId).map { toDomain(it) }
    }

    fun selectByApplyedUserId(userId: Int): List<Lesson> {
        val lessonList = lessonMapper.selectByApplyedUserId(userId)
        return lessonList.map { toDomain(it) }
    }

    fun apply(id: Int, userId: Int) {
        val userApplyLessonEntity = UserApplyLessonEntity(id, userId, Date())
        userApplyLessonMapper.insert(userApplyLessonEntity)
    }

    fun canApply(lesson: Lesson, userId: Int): Boolean {
        if (lesson.isOpen && lesson.eventDatetime.after(Date()) && !isApply(lesson, userId)) {
            return true
        }

        return false
    }

    fun isApply(lesson: Lesson, userId: Int): Boolean {
        val userApplyLessonEntity = userApplyLessonMapper.selectByUserIdAndLessonId(lesson.id, userId)
        if (userApplyLessonEntity is UserApplyLessonEntity) {
            return true
        }

        return false
    }

    private fun toDomain(lessonEntity: LessonEntity?): Lesson
    {
        if (lessonEntity is LessonEntity) {
            if (lessonEntity.id is Int && lessonEntity.title is String && lessonEntity.eventDatetime is Date && lessonEntity.price is Int && lessonEntity.description is String && lessonEntity.emailAddress is String && lessonEntity.isOpen is Boolean) {
                val imageUrl = if (lessonEntity.imageUrl is String && lessonEntity.imageUrl.isNotEmpty()) lessonEntity.imageUrl else "https://1.bp.blogspot.com/-Iv3bczeEefY/WxvJvlRTqEI/AAAAAAABMjc/9Rw8cVYk4B8P8_bcvXoA6gpLuByjtsPdQCLcBGAs/s400/computer_school_boy.png"
                if (lessonEntity.ownerId is Int) {
                    val user: User = userService.select(lessonEntity.ownerId)

                    return Lesson(lessonEntity.id, lessonEntity.title, lessonEntity.eventDatetime, lessonEntity.price, lessonEntity.description, lessonEntity.emailAddress, imageUrl, user.accountName, lessonEntity.ownerId, user.iconImageUrl, lessonEntity.isOpen)
                }
            }

            throw LessonServiceException("必要なカラムにnullが含まれています")
        }

        throw LessonServiceException("lessonが見つかりませんでした")
    }

    class LessonServiceException(s: String) : Exception()
    // TODO kotlinでのexceptionクラスの作り方に直す
}