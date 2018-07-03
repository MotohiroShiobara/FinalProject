package com.final.project.Teechear.mapper

import com.final.project.Teechear.domain.Article
import org.apache.ibatis.annotations.Mapper

@Mapper
interface ArticleMapper {

    fun insert(article: Article)

    fun find(id: Int): Article?
}