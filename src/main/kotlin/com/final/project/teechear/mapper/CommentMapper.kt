package com.final.project.teechear.mapper

import com.final.project.teechear.entity.CommentEntity
import org.apache.ibatis.annotations.Mapper

@Mapper
interface CommentMapper {

    fun insert(comment: CommentEntity)

    fun selectByArticleId(articleId: Int): List<CommentEntity>

    fun selectIdAndByArticleIdAndUserId(id: Int, userId: Int, articleId: Int): CommentEntity?

    fun update(commentEntity: CommentEntity): Int
}