package com.final.project.teechear.mapper

import com.final.project.teechear.entity.LessonEntity
import org.apache.ibatis.annotations.Mapper

@Mapper
interface LessonMapper {

    fun insert(lesson: LessonEntity)

    fun select(id: Int): LessonEntity?

    fun selectByOwnerId(ownerId: Int): List<LessonEntity>

    fun openByOwnerId(ownerId: Int): List<LessonEntity>

    fun closeByOwnerId(ownerId: Int): List<LessonEntity>

    fun selectByApplyedUserId(userId: Int): List<LessonEntity>

    fun close(id: Int)

    fun selectMostRecentByUserId(userId: Int): LessonEntity?

    fun trend(): List<LessonEntity>

    fun search(query: String): List<LessonEntity>

    fun delete(id: Int, ownerId: Int): Int

    fun searchCount(escapeQuery: String): Int

    fun searchByPaginate(query: String, offset: Int, limit: Int): List<LessonEntity>
}