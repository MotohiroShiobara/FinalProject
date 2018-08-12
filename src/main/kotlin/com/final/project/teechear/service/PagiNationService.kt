package com.final.project.teechear.service

import com.final.project.teechear.domain.PagiNate
import com.final.project.teechear.exception.PageNotFoundException
import com.final.project.teechear.util.Pagination
import org.springframework.stereotype.Service

@Service
class PagiNationService(private val pagination: Pagination) {

    // TODO paginate.perPageCountに満たない検索結果数の場合はnullを返す
    @Throws(PageNotFoundException::class)
    fun obtainPaginate(resultCount: Int, nullableCurrentPage: Int?, perPageSize: Int): PagiNate {
        // resultCountがperPageSizeに満たなかった場合
        if (perPageSize > resultCount) {
            return PagiNate(
                    pageSize = resultCount,
                    currentPage = 1,
                    perPageSize = perPageSize,
                    lastPage = 1
            )
        }

        val lastPage = pagination.calcLastPage(pageSize = resultCount, perPageSize = perPageSize)
        val currentPage = nullableCurrentPage ?: 1
        if (lastPage < currentPage || currentPage <= 0) {
            throw PageNotFoundException("指定されたページ数: $currentPage")
        }

        return PagiNate(
                pageSize = resultCount,
                currentPage = currentPage,
                perPageSize = perPageSize,
                lastPage = lastPage)
    }
}
