package com.final.project.Teechear.domain

import java.util.*

data class TrendLesson(val id: Int,
                       val title: String,
                       val eventDatetime: Date,
                       val price: Int,
                       val imageUrl: String,
                       val userName: String,
                       val ownerId: Int)