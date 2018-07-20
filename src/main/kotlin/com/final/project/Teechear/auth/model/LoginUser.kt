package com.final.project.Teechear.auth.model

/**
 * Created by version1 on 2017/05/26.
 */
import com.final.project.Teechear.entity.UserEntity
import org.springframework.security.core.authority.AuthorityUtils

/**
 * 認証ユーザーの情報を格納するクラス
 */
class LoginUser (user: UserEntity): org.springframework.security.core.userdetails.User(
        user.emailAddress,
        user.password,
        AuthorityUtils.createAuthorityList("ROLE_USER")) {
    /**
     * ログインユーザー
     */
    var loginUser: UserEntity? = null
    init {
        // スーパークラスのユーザーID、パスワードに値をセットする
        // 実際の認証はスーパークラスのユーザーID、パスワードで行われる
        this.loginUser = user
    }

}