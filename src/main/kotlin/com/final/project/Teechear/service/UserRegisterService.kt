package com.final.project.Teechear.service

import com.final.project.Teechear.mapper.UserMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class UserRegisterService(private val userMapper: UserMapper) {

    fun validation(email: String, accountName: String) {
        if (userMapper.findByEmailOrName(email) != null) {
            // TODO メールアドレスがユーザー名もしくはEmailとして登録済みであることのExceptionを発生させる
        }

        if (userMapper.findByEmailOrName(accountName) != null) {
            // TODO ユーザー名がユーザー名もしくはEmailとしてすでに
        }
    }
}