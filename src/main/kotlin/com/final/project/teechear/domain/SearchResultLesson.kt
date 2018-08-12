package com.final.project.teechear.domain

import java.util.*

data class SearchResultLesson(
        val id: Int,
        val title: String,
        val eventDatetime: Date,
        val price: Int,
        val imageUrl: String,
        val userName: String,
        val ownerId: Int
)
