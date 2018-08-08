package com.final.project.Teechear.domainConverter

import com.final.project.Teechear.domain.User
import com.final.project.Teechear.entity.UserEntity
import com.final.project.Teechear.exception.DomainArgumentException
import org.springframework.stereotype.Component

@Component
class UserDomainConverter {

    fun toDomain(userEntity: UserEntity?): User? {
        if (userEntity !is UserEntity) return null
        if (userEntity.id is Int && userEntity.accountName is String) {
            User(
                    userEntity.id,
                    userEntity.accountName,
                    userEntity.iconImageUrl ?: "https://avatars2.githubusercontent.com/u/38315670?s=460&v=4",
                    userEntity.profile ?: "")
        }

        throw DomainArgumentException("userEntity: $userEntity")
    }
}