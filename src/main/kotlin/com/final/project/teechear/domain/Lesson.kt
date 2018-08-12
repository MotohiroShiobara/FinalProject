package com.final.project.teechear.domain

import java.util.*

data class Lesson(
        val id: Int,
        val title: String,
        val eventDatetime: Date,
        val price: Int,
        val description: String,
        val emailAddress: String,
        val imageUrl: String,
        val userName: String,
        val ownerId: Int,
        val userIconImageUrl: String,
        val isOpen: Boolean,
        val estimatedTime: Int? = null
)
