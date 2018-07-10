package com.final.project.Teechear.mapper

import com.final.project.Teechear.domain.Comment
import org.apache.ibatis.annotations.Mapper

@Mapper
interface CommentMapper {

    fun insert(comment: Comment)

    fun selectByArticleId(articleId: Int): List<Comment>
}