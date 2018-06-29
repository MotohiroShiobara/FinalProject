package com.final.project.Qiita.mapper

import com.final.project.Qiita.domain.User
import org.apache.ibatis.annotations.Mapper

@Mapper
interface UserMapper {
    
    fun insert(user: User)

    fun select(id: Int): User

    fun selectByUserName(accountName: String): User

    fun all(): List<User>

    fun delete(id: Int)

    fun update(user: User)

    fun findByEmail(emailAddress: String): User

    fun findByEmailOrName(loginName: String): User
}