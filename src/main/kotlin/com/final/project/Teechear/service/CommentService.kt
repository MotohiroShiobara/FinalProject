package com.final.project.Teechear.service

import com.final.project.Teechear.domain.Comment
import com.final.project.Teechear.domain.User
import com.final.project.Teechear.entity.CommentEntity
import com.final.project.Teechear.entity.UserEntity
import com.final.project.Teechear.mapper.CommentMapper
import com.final.project.Teechear.mapper.UserMapper
import org.springframework.stereotype.Service


@Service
class CommentService(
        private val userService: UserService,
        private val commentMapper: CommentMapper,
        private val userMapper: UserMapper) {

    fun commentListByArticle(articleId: Int): List<Comment> {
        return commentMapper.selectByArticleId(articleId).map { toDomain(it) }
    }

    private fun toDomain(commentEntity: CommentEntity?): Comment {
        if (commentEntity is CommentEntity) {

            if (commentEntity.markdownText is String && commentEntity.userId is Int) {
                val userEntity = userMapper.select(commentEntity.userId)
                val user = userService.toDomain(userEntity)

                return Comment(commentEntity.markdownText, user.accountName, user.iconImageUrl)
            }

            throw CommentServiceException("commentに必要なカラムが不足している")
        }

        throw IllegalArgumentException("commentEntityが見つかりません")
    }

    class CommentServiceException(message: String) : Exception()
}