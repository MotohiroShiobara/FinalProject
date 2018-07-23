package com.final.project.Teechear.mapper

import com.final.project.Teechear.entity.UserLikeArticleEntity
import org.apache.ibatis.annotations.Mapper

@Mapper
interface UserLikeArticleMapper {

    fun insert(userLikeArticle: UserLikeArticleEntity)

    fun articleLikeCount(articleId: Int): Int

    fun findByUserIdAndArticleId(articleId: Int, userId: Int): UserLikeArticleEntity?

    fun delete(articleId: Int, userId: Int)
}