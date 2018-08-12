package com.final.project.teechear.service

import com.final.project.teechear.domain.Comment
import com.final.project.teechear.domainConverter.CommentDomainConverter
import com.final.project.teechear.mapper.CommentMapper
import org.springframework.stereotype.Service


@Service
class CommentService(
        private val commentMapper: CommentMapper,
        private val commentDomainConverter: CommentDomainConverter) {

    fun commentListByArticle(articleId: Int): List<Comment> {
        return commentMapper.selectByArticleId(articleId).mapNotNull { commentDomainConverter.toDomain(it) }
    }
}
