package com.final.project.Qiita.mapper

import com.final.project.Qiita.domain.Article
import org.apache.ibatis.annotations.Mapper

@Mapper
interface ArticleMapper {

    fun insert(article: Article)

    fun select(id: Int)
}