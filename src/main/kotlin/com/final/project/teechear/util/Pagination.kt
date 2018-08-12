package com.final.project.teechear.util

import com.final.project.teechear.domain.PagiNate
import org.springframework.stereotype.Component

@Component
class Pagination {
    
    fun obtainRange(paginate: PagiNate): Range {
        if (paginate.currentPage == 0 || paginate.currentPage == 1) return Range(offset = 0, to = if (paginate.pageSize > 20) 20 else paginate.pageSize)

        val toSize = paginate.perPageSize * paginate.currentPage

        // 指定されたページが最終ページもしくは最終ページ以上を指定した場合
        if (paginate.lastPage <= paginate.currentPage) return Range(offset = (paginate.lastPage * paginate.perPageSize) - paginate.perPageSize, to = paginate.pageSize)

        return Range(offset = toSize - paginate.perPageSize, to = toSize)
    }

    /**
     * 最終ページが何ページかを返す
     */
    fun calcLastPage(pageSize: Int, perPageSize: Int): Int {
        return Math.ceil(pageSize.div(perPageSize.toDouble())).toInt()
    }

    class Range(val offset: Int, val to: Int)
}
