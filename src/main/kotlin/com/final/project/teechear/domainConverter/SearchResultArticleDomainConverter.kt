package com.final.project.teechear.domainConverter

import com.final.project.teechear.domain.SearchResultArticle
import com.final.project.teechear.entity.ArticleEntity
import com.final.project.teechear.entity.UserEntity
import com.final.project.teechear.mapper.UserLikeArticleMapper
import com.final.project.teechear.mapper.UserMapper
import org.springframework.stereotype.Component

@Component
class SearchResultArticleDomainConverter(private val userMapper: UserMapper, private val userLikeArticleMapper: UserLikeArticleMapper) {

    fun toDomain(article: ArticleEntity?): SearchResultArticle {
        if (article is ArticleEntity) {
            if (
                    article.id is Int &&
                    article.title is String &&
                    article.userId is Int
            ) {
                val user = userMapper.select(article.userId)
                val likeCount = userLikeArticleMapper.articleLikeCount(article.id)

                if (user is UserEntity && user.accountName is String) {
                    if (user.iconImageUrl is String) {
                        return SearchResultArticle(article.id, article.title, article.userId, user.accountName, likeCount, user.iconImageUrl)
                    } else {
                        return SearchResultArticle(article.id, article.title, article.userId, user.accountName, likeCount)
                    }
                }
            }

            throw Exception("Articleに必要なカラムが不足しています")
        } else {
            throw Exception("artcleが存在しません")
        }
    }
}