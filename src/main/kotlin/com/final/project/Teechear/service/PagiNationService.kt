package com.final.project.Teechear.service

import com.final.project.Teechear.domain.PagiNate
import com.final.project.Teechear.exception.PageNotFoundException
import com.final.project.Teechear.util.Pagination
import org.springframework.stereotype.Service

@Service
class PagiNationService(private val pagination: Pagination) {

    @Throws(PageNotFoundException::class)
    fun obtainPaginate(resultCount: Int, nullableCurrentPage: Int?, perPageSize: Int): PagiNate {
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
