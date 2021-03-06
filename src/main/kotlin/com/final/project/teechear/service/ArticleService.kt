package com.final.project.teechear.service

import com.final.project.teechear.domain.Article
import com.final.project.teechear.domain.CreateArticle
import com.final.project.teechear.domain.UpdateArticle
import com.final.project.teechear.domainConverter.ArticleDomainConverter
import com.final.project.teechear.entity.ArticleEntity
import com.final.project.teechear.exception.ResourceNotFoundException
import com.final.project.teechear.mapper.ArticleMapper
import com.final.project.teechear.mapper.UserLikeArticleMapper
import com.final.project.teechear.mapper.UserMapper
import com.final.project.teechear.util.EscapeStringConverter
import org.springframework.stereotype.Service
import java.sql.SQLException
import java.util.*

@Service
class ArticleService(
        private val articleMapper: ArticleMapper,
        private val userMapper: UserMapper,
        private val userLikeArticleMapper: UserLikeArticleMapper,
        private val userService: UserService,
        private val articleDomainConverter: ArticleDomainConverter) {

    fun findById(id: Int): Article? {
        return articleDomainConverter.toDomain(articleMapper.find(id))
    }

    fun userArticleList(userId: Int): List<Article> {
        val articleEntityList = articleMapper.selectByUserId(userId)
        return articleEntityList.map { toDomain(it) }
    }

    fun search(query: String): List<Article> {
        val escapeQuery = EscapeStringConverter.searchQuery(query)
        return articleMapper.search(escapeQuery).map { toDomain(it) }
    }

    @Throws(SQLException::class)
    fun create(createArticle: CreateArticle): Int {
        val article = ArticleEntity(createArticle.title, createArticle.userId, Date(), createArticle.markdownText)
        val result = articleMapper.insert(article)
        if (!result) throw SQLException()
        return article.id!!
    }

    fun update(argUpdateArticle: UpdateArticle) {
        val article = articleMapper.findByIdAndUserId(argUpdateArticle.id, argUpdateArticle.userId)
                ?: throw ResourceNotFoundException("article_idが見つかりません")
        val updateArticle = article.copy(title = argUpdateArticle.title, markdownText = argUpdateArticle.markdownText)
        val result = articleMapper.update(updateArticle)
        if (result != 1) {
            throw SQLException()
        }
    }

    fun delete(id: Int, currentUserId: Int) {
        val article = articleMapper.findByIdAndUserId(id, currentUserId)
                ?: throw ResourceNotFoundException("articleが見つかりません")
        val result = articleMapper.delete(article.id!!, currentUserId)
        if (result == 0) {
            throw ResourceNotFoundException("articleが見つかりません")
        } else if (result != 1) {
            throw SQLException()
        }
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
            throw ArticleNotFoundException("artcleが存在しません")
        }
    }

    class ArticleServiceException(s: String) : Exception()

    class ArticleNotFoundException(s: String) : Exception()
}