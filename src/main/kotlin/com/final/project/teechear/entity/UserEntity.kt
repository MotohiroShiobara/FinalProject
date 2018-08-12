package com.final.project.teechear.entity

data class UserEntity(
        val accountName: String? = null,
        val emailAddress: String? = null,
        val password: String? = null,
        val profile: String? = null,
        val id: Int? = null,
        val articleCount: Int? = null,
        val iconImageUrl: String? = null,
        val userApplyLesson: UserApplyLessonEntity? = null)
