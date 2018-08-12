package com.final.project.teechear.service

import com.final.project.teechear.domain.User
import com.final.project.teechear.entity.UserLikeArticleEntity
import com.final.project.teechear.mapper.ArticleMapper
import com.final.project.teechear.mapper.UserLikeArticleMapper
import com.final.project.teechear.mapper.UserMapper
import org.springframework.stereotype.Service
import java.security.Principal

@Service
class ArticleLikeService(private val userLikeArticleMapper: UserLikeArticleMapper,
                         private val userMapper: UserMapper,
                         private val articleMapper: ArticleMapper) {

    fun likeCount(articleId: Int): Int {
        return userLikeArticleMapper.articleLikeCount(articleId)
    }

    fun isLikedByUser(articleId: Int, currentUserId: Int): Boolean {
        return userLikeArticleMapper.findByUserIdAndArticleId(articleId, currentUserId) is UserLikeArticleEntity
    }

    fun create(articleId: Int, principal: Principal) {
        val currentUser = userMapper.findByEmailOrName(principal.name)
        val currentUserId = currentUser?.id!!
        if (validation(articleId, currentUserId)) {
            val like = UserLikeArticleEntity(currentUserId, articleId)
            userLikeArticleMapper.insert(like)
        }
    }

    fun delete(articleId: Int, user: User) {
        userLikeArticleMapper.delete(articleId, user.id)
    }

    private fun validation(articleId: Int, userId: Int):Boolean {
        if (userLikeArticleMapper.findByUserIdAndArticleId(articleId, userId) is UserLikeArticleEntity) {
            return false
        }

        // そのArticleのuser_idがいいねしたユーザーの場合、無効にする
        if (articleMapper.find(articleId)?.userId == userId) {
            return false
        }

        return true
    }
}