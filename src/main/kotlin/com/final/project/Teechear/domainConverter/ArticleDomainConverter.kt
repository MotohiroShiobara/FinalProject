package com.final.project.Teechear.domainConverter

import com.final.project.Teechear.domain.Article
import com.final.project.Teechear.entity.ArticleEntity
import com.final.project.Teechear.exception.DomainArgumentException
import com.final.project.Teechear.mapper.UserLikeArticleMapper
import com.final.project.Teechear.mapper.UserMapper
import org.springframework.stereotype.Component
import java.util.*

@Component
class ArticleDomainConverter(
        private val userMapper: UserMapper,
        private val userLikeArticleMapper: UserLikeArticleMapper) {

    fun toDomain(article: ArticleEntity?): Article? {
        if (article !is ArticleEntity) return null

        if (
                article.id is Int &&
                article.title is String &&
                article.releasedAt is Date &&
                article.userId is Int &&
                article.markdownText is String
        ) {
            val userEntity = userMapper.select(article.userId)
            val likeCount = userLikeArticleMapper.articleLikeCount(article.id)

            if (userEntity?.accountName is String) {
                return Article(
                        article.id,
                        article.title,
                        article.releasedAt,
                        article.userId,
                        userEntity.accountName,
                        article.markdownText,
                        likeCount,
                        userEntity.iconImageUrl ?: "")
            }
        }

        throw DomainArgumentException("articleの中身: $article")
    }
}
