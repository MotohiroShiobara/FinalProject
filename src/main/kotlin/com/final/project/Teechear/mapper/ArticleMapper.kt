package com.final.project.Teechear.mapper

import com.final.project.Teechear.domain.Article
import org.apache.ibatis.annotations.Mapper

@Mapper
interface ArticleMapper {

    fun insert(article: Article)

    fun find(id: Int): Article?

    /**
     * 一週間以内に投稿された記事の中からいいね数が多いものから20件取得する
     */
    fun trend(): List<Article>
}