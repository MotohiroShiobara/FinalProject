package com.final.project.Teechear.mapper

import org.apache.ibatis.annotations.Mapper

@Mapper
interface UserLikeArticleMapper {

    fun articleLikeCount(articleId: Int): Int
}