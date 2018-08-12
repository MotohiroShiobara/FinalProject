package com.final.project.teechear.mapper

import com.final.project.teechear.entity.UserApplyLessonEntity
import org.apache.ibatis.annotations.Mapper

@Mapper
interface UserApplyLessonMapper {

    fun insert(userApplyLessonEntity: UserApplyLessonEntity)

    fun selectByUserIdAndLessonId(lessonId: Int, userId: Int): UserApplyLessonEntity?

    fun selectByLessonIds(lessonIds: List<Int>): List<UserApplyLessonEntity>
}