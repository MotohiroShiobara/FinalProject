package com.final.project.Teechear.auth.model

/**
 * Created by version1 on 2017/05/26.
 */
import com.final.project.Teechear.domain.User
import org.springframework.security.core.authority.AuthorityUtils

/**
 * 認証ユーザーの情報を格納するクラス
 */
class LoginUser (user: User): org.springframework.security.core.userdetails.User(
        user.emailAddress,
        user.password,
        AuthorityUtils.createAuthorityList("ROLE_USER")) {
    /**
     * ログインユーザー
     */
    var loginUser: User? = null
    init {
        println(user.emailAddress)
        // スーパークラスのユーザーID、パスワードに値をセットする
        // 実際の認証はスーパークラスのユーザーID、パスワードで行われる
        this.loginUser = user
    }

}