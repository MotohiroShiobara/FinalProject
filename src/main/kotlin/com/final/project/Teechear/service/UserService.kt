package com.final.project.Teechear.service

import com.final.project.Teechear.domain.User
import com.final.project.Teechear.entity.UserEntity
import com.final.project.Teechear.mapper.UserMapper
import org.springframework.stereotype.Service
import java.security.Principal

@Service
class UserService(private val userMapper: UserMapper) {

    fun currentUser(principal: Principal): User {
        val userEntity: UserEntity? = userMapper.findByEmailOrName(principal.name)
        if (userEntity == null) {
            throw UserServiceException("userが見つかりません")
        }

        if (userEntity.id is Int && userEntity.accountName is String) {
            return User(userEntity.id, userEntity.accountName)
        }

        throw UserServiceException("userに必要なカラムが不足しています")
    }

    fun toDomain(userEntity: UserEntity): User {
        if (userEntity.id is Int && userEntity.accountName is String) {
            return User(userEntity.id, userEntity.accountName)
        }

        throw UserServiceException("userに必要なカラムが不足しています")
    }

    class UserServiceException(s : String): Exception()
}