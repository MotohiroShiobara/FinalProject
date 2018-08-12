package com.final.project.teechear.domainConverter

import com.final.project.teechear.domain.Comment
import com.final.project.teechear.entity.CommentEntity
import com.final.project.teechear.exception.DomainArgumentException
import com.final.project.teechear.mapper.UserMapper
import org.springframework.stereotype.Component

@Component
class CommentDomainConverter(private val userMapper: UserMapper) {

    fun toDomain(commentEntity: CommentEntity?): Comment? {
        if (commentEntity !is CommentEntity) return null
        if (commentEntity.markdownText !is String || commentEntity.userId !is Int)
            throw DomainArgumentException("commentEntity: $commentEntity")

        val userEntity = userMapper.select(commentEntity.userId)
        if (userEntity?.accountName !is String) throw DomainArgumentException("userEntity: $userEntity")
        return Comment(
                commentEntity.markdownText,
                userEntity.accountName,
                userEntity.iconImageUrl ?: "",
                userEntity.id!!)
    }
}
