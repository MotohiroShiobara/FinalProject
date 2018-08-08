package com.final.project.Teechear.service

import com.final.project.Teechear.entity.UserLikeArticleEntity
import com.final.project.Teechear.mapper.UserLikeArticleMapper
import org.springframework.stereotype.Service

@Service
class ArticleLikeService(private val userLikeArticleMapper: UserLikeArticleMapper) {

    fun likeCount(articleId: Int): Int {
        return userLikeArticleMapper.articleLikeCount(articleId)
    }

    fun isLikedByUser(articleId: Int, currentUserId: Int): Boolean {
        return userLikeArticleMapper.findByUserIdAndArticleId(articleId, currentUserId) is UserLikeArticleEntity
    }
}