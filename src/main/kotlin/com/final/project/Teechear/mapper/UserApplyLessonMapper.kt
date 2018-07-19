package com.final.project.Teechear.mapper

import com.final.project.Teechear.entity.UserApplyLessonEntity
import org.apache.ibatis.annotations.Mapper

@Mapper
interface UserApplyLessonMapper {

    fun insert(userApplyLessonEntity: UserApplyLessonEntity)

    fun selectByUserIdAndLessonId(lessonId: Int, userId: Int): UserApplyLessonEntity?
}