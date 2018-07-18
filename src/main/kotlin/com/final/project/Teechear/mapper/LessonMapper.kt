package com.final.project.Teechear.mapper

import com.final.project.Teechear.entity.LessonEntity
import org.apache.ibatis.annotations.Mapper

@Mapper
interface LessonMapper {

    fun insert(lesson: LessonEntity)
}