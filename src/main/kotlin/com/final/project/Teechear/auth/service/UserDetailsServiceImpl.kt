package com.final.project.Teechear.auth.service

import com.final.project.Teechear.auth.model.LoginUser
import com.final.project.Teechear.entity.UserEntity
import com.final.project.Teechear.mapper.UserMapper
import org.springframework.beans.factory.annotation.Autowired

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component

/**
 * UserDetailsServiceの実装クラス
 * Spring Securityでのユーザー認証に使用する
 */
@Component
open class UserDetailsServiceImpl :UserDetailsService {

    @Autowired
    lateinit var userMapper: UserMapper

    override fun loadUserByUsername(loginName : String ) : UserDetails{
        // 認証を行うユーザー情報を格納する
        var user : UserEntity?  = null
        try {
            // 入力したユーザーIDから認証を行うユーザー情報を取得する
            user = userMapper.findByEmailOrName(loginName)
        } catch (e:Exception ) {
            // 取得時にExceptionが発生した場合
            throw UsernameNotFoundException("It can not be acquired UserEntity");
        }

        // ユーザー情報を取得できなかった場合
        if(user == null){
            throw UsernameNotFoundException("UserEntity not found for login id: " + loginName);
        }

        // ユーザー情報が取得できたらSpring Securityで認証できる形で戻す
        return LoginUser(user)
    }

}