package com.final.project.Teechear.service

import com.final.project.Teechear.domain.PagiNate
import com.final.project.Teechear.util.Pagination
import org.springframework.stereotype.Service

@Service
class PagiNationService(private val pagination: Pagination) {

    fun obtainPaginate(resultCount: Int, currentPage: Int?, perPageSize: Int): PagiNate {
        val lastPage = pagination.calcLastPage(pageSize = resultCount, perPageSize = perPageSize)
        return PagiNate(
                pageSize = resultCount,
                currentPage = currentPage ?: 1,
                perPageSize = perPageSize,
                lastPage = lastPage)
    }
}


