package com.final.project.Teechear.validate

import java.util.*

data class lessonNewForm(
        val title: String? = null,
        val price: Int? = null,
        val eventDateTime: Date? = null,
        val description: String? = null,
        val emailAddress: String? = null,
        val imageUrl: String? = null
)
