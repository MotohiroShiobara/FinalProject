package com.final.project.Teechear.service

import com.final.project.Teechear.domain.UserLikeArticle
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
            val like = UserLikeArticle(currentUserId, articleId)
            userLikeArticleMapper.insert(like)
        }
    }

    private fun validation(articleId: Int, userId: Int):Boolean {
        if (userLikeArticleMapper.findByUserIdAndArticleId(articleId, userId) is UserLikeArticle) {
            return false
        }

        // そのArticleのuser_idがいいねしたユーザーの場合、無効にする
        if (articleMapper.find(articleId)?.userId == userId) {
            return false
        }

        return true
    }
}