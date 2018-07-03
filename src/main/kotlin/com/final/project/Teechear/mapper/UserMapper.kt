package com.final.project.Teechear.mapper

import com.final.project.Teechear.domain.User
import org.apache.ibatis.annotations.Mapper

@Mapper
interface UserMapper {
    
    fun insert(user: User)

    fun select(id: Int): User

    fun selectByUserName(accountName: String): User

    fun selectAll(): List<User>

    fun delete(id: Int)

    fun update(user: User)

    fun findByEmail(emailAddress: String): User

    fun findByEmailOrName(loginName: String): User
}