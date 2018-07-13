package com.final.project.Teechear.service

import com.final.project.Teechear.domain.Article
import com.final.project.Teechear.entity.ArticleEntity
import com.final.project.Teechear.entity.UserEntity
import com.final.project.Teechear.mapper.ArticleMapper
import com.final.project.Teechear.mapper.UserLikeArticleMapper
import com.final.project.Teechear.mapper.UserMapper
import org.springframework.stereotype.Service
import java.util.*

@Service
class ArticleService(
        private val articleMapper: ArticleMapper,
        private val userMapper: UserMapper,
        private val userLikeArticleMapper: UserLikeArticleMapper) {

    fun userArticleList(userId: Int): List<Article> {
        val articleEntityList = articleMapper.selectByUserId(userId)
        return articleEntityList.map { toDomain(it) }
    }

    fun trendArticleList(): List<Article> {
        val articleEntityList = articleMapper.trend()
        return articleEntityList.map { toDomain(it) }
    }

    private fun toDomain(article: ArticleEntity): Article {
        if (
                article.id is Int &&
                article.title is String &&
                article.releasedAt is Date &&
                article.userId is Int
        ) {
            val user = userMapper.select(article.userId)
            if (user is UserEntity) {
                val likeCount = userLikeArticleMapper.articleLikeCount(article.id)

                if (user.accountName is String) {
                    return Article(article.id, article.title, article.releasedAt, user.accountName, likeCount)
                }

                throw ArticleServiceException("user.accountNameがnullの可能性があります")
            }

            throw ArticleServiceException("userの取得に失敗しました")
        }

        throw ArticleServiceException("Articleに必要なカラムが不足しています")
    }

    class ArticleServiceException(s : String) : Exception()
}