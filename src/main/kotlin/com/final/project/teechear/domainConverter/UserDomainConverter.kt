package com.final.project.teechear.domainConverter

import com.final.project.teechear.domain.User
import com.final.project.teechear.entity.UserEntity
import com.final.project.teechear.exception.DomainArgumentException
import org.springframework.stereotype.Component

@Component
class UserDomainConverter {

    fun toDomain(userEntity: UserEntity?): User? {
        if (userEntity !is UserEntity) return null
        if (userEntity.id is Int && userEntity.accountName is String) {
            return User(
                    userEntity.id,
                    userEntity.accountName,
                    userEntity.iconImageUrl ?: "https://avatars2.githubusercontent.com/u/38315670?s=460&v=4",
                    userEntity.profile ?: "")
        }

        throw DomainArgumentException("userEntity: $userEntity")
    }
}