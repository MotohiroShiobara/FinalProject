package com.final.project.Teechear.domain

data class TrendArticle(val id: Int,
                        val title: String,
                        val userId: Int,
                        val userName: String,
                        val likeCount: Int,
                        val iconImageUrl: String = "https://avatars2.githubusercontent.com/u/38315670?s=460&v=4")