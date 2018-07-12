package com.final.project.Teechear.mapper

import com.final.project.Teechear.domain.UserLikeArticle
import org.apache.ibatis.annotations.Mapper

@Mapper
interface UserLikeArticleMapper {

    fun insert(userLikeArticle: UserLikeArticle)

    fun articleLikeCount(articleId: Int): Int

    fun findByUserId(articleId: Int, userId: Int): UserLikeArticle?
}