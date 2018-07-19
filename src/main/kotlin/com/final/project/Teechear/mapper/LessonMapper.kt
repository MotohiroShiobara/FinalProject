package com.final.project.Teechear.mapper

import com.final.project.Teechear.entity.LessonEntity
import org.apache.ibatis.annotations.Mapper

@Mapper
interface LessonMapper {

    fun insert(lesson: LessonEntity)

    fun select(id: Int): LessonEntity?

    fun openByOwnerId(ownerId: Int): List<LessonEntity>

    fun closeByOwnerId(ownerId: Int): List<LessonEntity>

    fun selectByApplyedUserId(userId: Int): List<LessonEntity>

    fun close(id: Int)
}