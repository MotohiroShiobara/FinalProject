package com.final.project.Teechear.service

import com.final.project.Teechear.domain.Lesson
import com.final.project.Teechear.domain.User
import com.final.project.Teechear.entity.LessonEntity
import com.final.project.Teechear.entity.UserApplyLessonEntity
import com.final.project.Teechear.exception.ResourceNotFoundException
import com.final.project.Teechear.mapper.LessonMapper
import com.final.project.Teechear.mapper.UserApplyLessonMapper
import com.final.project.Teechear.form.LessonNewForm
import com.final.project.Teechear.util.EscapeStringConverter
import org.springframework.stereotype.Service
import org.springframework.validation.BindingResult
import org.springframework.validation.FieldError
import java.util.*

@Service
class LessonService(
        private val dateTimeService: DateTimeService,
        private val lessonMapper: LessonMapper,
        private val userService: UserService,
        private val userApplyLessonMapper: UserApplyLessonMapper) {

    fun createByForm(form: LessonNewForm, userId: Int, imageUrl: String): Int {
        if (form.eventDate is String && form.eventTime is String) {
            val eventDateTime = form.eventDate + "T" + form.eventTime // example: 2018-07-26T19:39
            val convertedEventDateTime = dateTimeService.toDate(eventDateTime)
            val lessonEntity = LessonEntity(
                    form.title,
                    convertedEventDateTime,
                    form.price,
                    form.description,
                    imageUrl,
                    form.emailAddress,
                    userId,
                    true,
                    form.estimatedTime)
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
        if (result.hasErrors()) {
            return result
        }

        // eventDatetimeは未来でなければならない
        if (form.eventDate is String && form.eventTime is String) {
            val eventDatetime = form.eventDate + "T" + form.eventTime // example: 2018-07-26T19:39
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

    fun selectByOwnerId(ownerId: Int): List<Lesson> {
        return lessonMapper.selectByOwnerId(ownerId).map { toDomain(it) }
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

    fun mostRecentLessonByUserId(userId: Int): Lesson? {
        val lessonEntity = lessonMapper.selectMostRecentByUserId(userId)
        if (lessonEntity is LessonEntity) {
            return toDomain(lessonEntity)
        }

        return null
    }

    @Throws(ResourceNotFoundException::class)
    fun delete(id: Int, ownerId: Int) {
        val result = lessonMapper.delete(id, ownerId)
        if (result == 0) {
            throw ResourceNotFoundException("lessonが見つかりません")
        }
    }

    fun isDeletable(lesson: Lesson, currentUserId: Int): Boolean {
        return lesson.ownerId == currentUserId && userApplyLessonMapper.selectByLessonIds(listOf(lesson.id)).count() == 0
    }

    private fun toDomain(lessonEntity: LessonEntity?): Lesson
    {
        if (lessonEntity is LessonEntity) {
            if (lessonEntity.id is Int && lessonEntity.title is String && lessonEntity.eventDatetime is Date && lessonEntity.price is Int && lessonEntity.description is String && lessonEntity.emailAddress is String && lessonEntity.isOpen is Boolean) {
                val imageUrl = if (lessonEntity.imageUrl is String && lessonEntity.imageUrl.isNotEmpty()) lessonEntity.imageUrl else "https://1.bp.blogspot.com/-Iv3bczeEefY/WxvJvlRTqEI/AAAAAAABMjc/9Rw8cVYk4B8P8_bcvXoA6gpLuByjtsPdQCLcBGAs/s400/computer_school_boy.png"
                if (lessonEntity.ownerId is Int) {
                    val user: User = userService.select(lessonEntity.ownerId)

                    return Lesson(lessonEntity.id, lessonEntity.title, lessonEntity.eventDatetime, lessonEntity.price, lessonEntity.description, lessonEntity.emailAddress, imageUrl, user.accountName, lessonEntity.ownerId, user.iconImageUrl, lessonEntity.isOpen, lessonEntity.estimatedTime)
                }
            }

            throw LessonServiceException("必要なカラムにnullが含まれています")
        }

        throw LessonNotFoundException("lessonが見つかりませんでした")
    }

    /**
     * データベースの構造上起こることはありえないException
     */
    class LessonServiceException(s: String) : Exception()
    // TODO kotlinでのexceptionクラスの作り方に直す

    class LessonNotFoundException(s: String): Exception()
}