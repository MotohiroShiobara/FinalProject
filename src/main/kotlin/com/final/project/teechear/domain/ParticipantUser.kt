package com.final.project.teechear.domain

import java.util.*

// lesson.title, user_apply_lesson.apply_datetime, user.name, user.id, user.iconImageUrl, lesson.id
data class ParticipantUser(
        val userId: Int,
        val lessonTitle: String,
        val applyDatetime: Date,
        val userName: String,
        val userIconImageUrl: String,
        val lessonId: Int)
