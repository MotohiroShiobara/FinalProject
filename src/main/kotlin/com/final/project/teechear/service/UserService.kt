package com.final.project.teechear.service

import com.final.project.teechear.domain.User
import com.final.project.teechear.domainConverter.UserDomainConverter
import com.final.project.teechear.entity.UserEntity
import com.final.project.teechear.form.UserEditForm
import com.final.project.teechear.mapper.UserMapper
import org.springframework.stereotype.Service
import java.security.Principal

@Service
class UserService(private val userMapper: UserMapper, private val userDomainConverter: UserDomainConverter) {

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

    fun update(userId: Int, userEditForm: UserEditForm, iconImageUrl: String) {
        val userEntity: UserEntity? = userMapper.select(userId)
        if (userEntity is UserEntity) {
            val userEntityCopy = userEntity.copy(accountName = userEditForm.accountName, profile = userEditForm.profile, iconImageUrl = iconImageUrl)
            userMapper.update(userEntityCopy)
        }
    }

    fun toDomain(userEntity: UserEntity?): User {
        if (userEntity !is UserEntity) {
            throw UserNotFoundException("ユーザーが見つかりませんでした")
        }

        if (userEntity.id is Int && userEntity.accountName is String) {
            return if (userEntity.iconImageUrl.isNullOrEmpty()) {
                User(userEntity.id, userEntity.accountName, "https://avatars2.githubusercontent.com/u/38315670?s=460&v=4", userEntity.profile ?: "")
            } else {
                User(userEntity.id, userEntity.accountName, userEntity.iconImageUrl!!, userEntity.profile ?: "")
            }
        }

        throw UserNotFoundException("userに必要なカラムが不足しています")
    }

    fun participant(lessonIds: List<Int>): List<User> {
        return userMapper.participant(lessonIds).map { toDomain(it) }
    }

    fun findByEmailOrAccountName(name: String): User? {
        val userEntity: UserEntity? = userMapper.findByEmailOrName(name)
        return userDomainConverter.toDomain(userEntity)
    }

    class UserServiceException(s: String) : Exception()

    class UserNotFoundException(s: String): Exception()
}