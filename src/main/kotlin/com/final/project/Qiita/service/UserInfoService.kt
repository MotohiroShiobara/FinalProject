package com.final.project.Qiita.service

import com.final.project.Qiita.mapper.UserMapper
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service


@Service
class UserInfoService : UserDetailsService {
    @Autowired
    private val userMapper: UserMapper? = null

    @Throws(UsernameNotFoundException::class)
    override fun loadUserByUsername(username: String?): UserDetails {
        if (username == null || "" == username) {
            throw UsernameNotFoundException("Username is empty")
        }

        return userMapper?.selectByUserName(username)
                ?: throw UsernameNotFoundException("User not found for name: $username")
    }
}