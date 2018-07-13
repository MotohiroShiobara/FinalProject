package com.final.project.Teechear.mapper

import com.final.project.Teechear.entity.ArticleEntity
import org.apache.ibatis.annotations.Mapper

@Mapper
interface ArticleMapper {

    fun insert(article: ArticleEntity)

    fun find(id: Int): ArticleEntity?

    /**
     * 一週間以内に投稿された記事の中からいいね数が多いものから20件取得する
     */
    fun trend(): List<ArticleEntity>

    /**
     * titleカラムの後方一致
     * TODO 全文検索をしたい(タグも含め)
     */
    fun search(query: String): List<ArticleEntity>

    fun selectByUserId(userId: Int): List<ArticleEntity>

    fun selectByUserIdPageNate(userId: Int, limit: Int = 15, offset: Int = 0): List<ArticleEntity>
}