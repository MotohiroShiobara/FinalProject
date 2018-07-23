package com.final.project.Teechear.service

import com.final.project.Teechear.domain.User
import com.final.project.Teechear.entity.UserLikeArticleEntity
import com.final.project.Teechear.mapper.ArticleMapper
import com.final.project.Teechear.mapper.UserLikeArticleMapper
import com.final.project.Teechear.mapper.UserMapper
import org.springframework.stereotype.Service
import java.security.Principal

@Service
class LikeService(
        private val userMapper: UserMapper,
        private val articleMapper: ArticleMapper,
        private val userLikeArticleMapper: UserLikeArticleMapper
) {

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