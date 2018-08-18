package com.final.project.teechear.service

import com.final.project.teechear.domain.Comment
import com.final.project.teechear.domain.UpdateComment
import com.final.project.teechear.domainConverter.CommentDomainConverter
import com.final.project.teechear.exception.ResourceNotFoundException
import com.final.project.teechear.mapper.CommentMapper
import org.springframework.stereotype.Service
import java.sql.SQLException


@Service
class CommentService(
        private val commentMapper: CommentMapper,
        private val commentDomainConverter: CommentDomainConverter) {

    fun commentListByArticle(articleId: Int): List<Comment> {
        return commentMapper.selectByArticleId(articleId).mapNotNull { commentDomainConverter.toDomain(it) }
    }

    @Throws(ResourceNotFoundException::class)
    fun update(argupdateComment: UpdateComment) {
        val commentEntity = commentMapper.selectIdAndByArticleIdAndUserId(
                argupdateComment.id,
                argupdateComment.userId,
                argupdateComment.articleId) ?: throw ResourceNotFoundException("commentが見つかりませんでした")
        val updateComment = commentEntity.copy(markdownText = argupdateComment.markdownText)
        val result = commentMapper.update(updateComment)
        if (result != 1) {
            throw SQLException()
        }
    }
}
