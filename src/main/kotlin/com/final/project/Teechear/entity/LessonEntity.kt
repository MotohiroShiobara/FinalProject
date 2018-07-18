package com.final.project.Teechear.entity

import java.util.*

data class LessonEntity(
        val title: String? = null,
        val eventDatetime: Date? = null,
        val price: Int? = null,
        val description: String? = null,
        val imageUrl: String? = null,
        val emailAddress: String? = null,
        val ownerId: Int? = null,
        val isOpen: Boolean? = null,
        val id: Int? = null
)