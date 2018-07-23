package com.final.project.Teechear.service

import com.final.project.Teechear.domain.Article
import com.final.project.Teechear.entity.ArticleEntity
import com.final.project.Teechear.mapper.ArticleMapper
import com.final.project.Teechear.mapper.UserLikeArticleMapper
import com.final.project.Teechear.mapper.UserMapper
import org.springframework.stereotype.Service
import java.lang.IllegalArgumentException
import java.util.*

@Service
class ArticleService(
        private val articleMapper: ArticleMapper,
        private val userMapper: UserMapper,
        private val userLikeArticleMapper: UserLikeArticleMapper,
        private val userService: UserService) {

    fun find(id: Int): Article {
        val articleEntity = articleMapper.find(id)
        return toDomain(articleEntity)
    }

    fun userArticleList(userId: Int): List<Article> {
        val articleEntityList = articleMapper.selectByUserId(userId)
        return articleEntityList.map { toDomain(it) }
    }

    fun trendArticleList(): List<Article> {
        val articleEntityList = articleMapper.trend()
        return articleEntityList.map { toDomain(it) }
    }

    fun search(query: String): List<Article> {
        return articleMapper.search(query).map { toDomain(it) }
    }

    private fun toDomain(article: ArticleEntity?): Article {
        if (article is ArticleEntity) {
            if (
                    article.id is Int &&
                    article.title is String &&
                    article.releasedAt is Date &&
                    article.userId is Int &&
                    article.markdownText is String
            ) {
                val userEntity = userMapper.select(article.userId)
                val user = userService.toDomain(userEntity)
                val likeCount = userLikeArticleMapper.articleLikeCount(article.id)

                return Article(article.id, article.title, article.releasedAt, article.userId, user.accountName, article.markdownText, likeCount, user.iconImageUrl)
            }

            throw ArticleServiceException("Articleに必要なカラムが不足しています")
        } else {
            throw IllegalArgumentException("artcleが存在しません")
        }
    }

    class ArticleServiceException(s: String) : Exception()
}