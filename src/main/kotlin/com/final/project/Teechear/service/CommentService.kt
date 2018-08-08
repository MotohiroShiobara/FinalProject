package com.final.project.Teechear.service

import com.final.project.Teechear.domain.Comment
import com.final.project.Teechear.domain.User
import com.final.project.Teechear.domainConverter.CommentDomainConverter
import com.final.project.Teechear.domainConverter.UserDomainConverter
import com.final.project.Teechear.entity.CommentEntity
import com.final.project.Teechear.entity.UserEntity
import com.final.project.Teechear.mapper.CommentMapper
import com.final.project.Teechear.mapper.UserMapper
import org.springframework.stereotype.Service


@Service
class CommentService(
        private val commentMapper: CommentMapper,
        private val commentDomainConverter: CommentDomainConverter) {

    fun commentListByArticle(articleId: Int): List<Comment> {
        return commentMapper.selectByArticleId(articleId).mapNotNull { commentDomainConverter.toDomain(it) }
    }
}
