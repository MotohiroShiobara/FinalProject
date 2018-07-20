package com.final.project.Teechear.service

import org.springframework.stereotype.Service

@Service
class PagiNationService {

    fun obtainPageList(currentPage: Int, contentsCount: Int, pagePerCount: Int): List<Int> {
        val stPage = if (currentPage == 1) 1 else currentPage - 1
        val endPage = currentPage + 4
        val maxPage = contentsCount / pagePerCount
        return if (endPage > maxPage) {
            (stPage..(maxPage)).map{it}
        } else {
            (stPage..(endPage)).map{it}
        }
    }
}