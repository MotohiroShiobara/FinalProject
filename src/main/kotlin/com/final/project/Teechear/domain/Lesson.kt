package com.final.project.Teechear.domain

import java.util.*

data class Lesson(
        val title: String,
        val eventDatetime: Date,
        val price: Int,
        val description: String,
        val emailAddress: String,
        val imageUrl: String,
        val userName: String,
        val ownerId: Int
)
