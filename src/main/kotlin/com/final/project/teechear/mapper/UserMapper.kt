package com.final.project.teechear.mapper

import com.final.project.teechear.entity.UserEntity
import org.apache.ibatis.annotations.Mapper

@Mapper
interface UserMapper {
    
    fun insert(user: UserEntity)

    fun select(id: Int): UserEntity?

    fun selectAll(): List<UserEntity>

    fun delete(id: Int)

    fun update(user: UserEntity)

    fun findByEmail(emailAddress: String): UserEntity?

    fun findByAccountName(accountName: String): UserEntity?

    fun findByEmailOrName(loginName: String): UserEntity?

    fun participant(lessonIds: List<Int>): List<UserEntity>
}