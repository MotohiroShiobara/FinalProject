package com.final.project.Teechear.mapper

import com.final.project.Teechear.entity.CommentEntity
import org.apache.ibatis.annotations.Mapper

@Mapper
interface CommentMapper {

    fun insert(comment: CommentEntity)

    fun selectByArticleId(articleId: Int): List<CommentEntity>
}