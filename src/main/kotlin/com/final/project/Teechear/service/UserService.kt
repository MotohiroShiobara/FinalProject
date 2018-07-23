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

        return toDomain(userEntity)
    }

    fun select(id: Int): User {
        return toDomain(userMapper.select(id))
    }

    fun toDomain(userEntity: UserEntity?): User {
        if (userEntity !is UserEntity) {
            throw UserServiceException("ユーザーが見つかりませんでした")
        }

        if (userEntity.id is Int && userEntity.accountName is String) {
            return if (userEntity.iconImageUrl.isNullOrEmpty()) {
                User(userEntity.id, userEntity.accountName, "https://avatars2.githubusercontent.com/u/38315670?s=460&v=4", userEntity.profile ?: "")
            } else {
                User(userEntity.id, userEntity.accountName, userEntity.iconImageUrl!!, userEntity.profile ?: "")
            }
        }

        throw UserServiceException("userに必要なカラムが不足しています")
    }

    class UserServiceException(s: String) : Exception()
}