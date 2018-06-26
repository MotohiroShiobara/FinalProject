package com.final.project.Qiita.mapper

import com.final.project.Qiita.domain.User
import org.apache.ibatis.annotations.Mapper

@Mapper
interface UserMapper {
    
    fun insert(user: User)

    fun select(id: Int): User

    fun all(): List<User>

    fun delete(id: Int)

    fun update(user: User)
}