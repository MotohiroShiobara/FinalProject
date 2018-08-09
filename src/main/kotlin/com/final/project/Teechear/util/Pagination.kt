package com.final.project.Teechear.util

import org.springframework.stereotype.Component

@Component
class Pagination {

    fun obtainRange(pageSize: Int, currentPage: Int, perPageSize: Int): Range {
        if (currentPage == 0 || currentPage == 1) return Range(offset = 0, to = if (pageSize > 20) 20 else pageSize)

        val toSize = perPageSize * currentPage
        val lastPage = calcLastPage(pageSize, perPageSize)

        // 指定されたページが最終ページもしくは最終ページ以上を指定した場合
        if (lastPage <= currentPage) return Range(offset = (lastPage * perPageSize) - perPageSize, to = pageSize)

        return Range(offset = toSize - perPageSize, to = toSize)
    }

    /**
     * 最終ページが何ページかを返す
     */
    private fun calcLastPage(pageSize: Int, perPageSize: Int): Int {
        return Math.ceil(pageSize.div(perPageSize.toDouble())).toInt()
    }

    class Range(val offset: Int, val to: Int)
}