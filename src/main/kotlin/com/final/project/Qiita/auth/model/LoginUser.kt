package com.final.project.Qiita.auth.model

/**
 * Created by version1 on 2017/05/26.
 */
import com.final.project.Qiita.domain.User
import org.springframework.security.core.authority.AuthorityUtils
import org.springframework.security.crypto.factory.PasswordEncoderFactories

/**
 * 認証ユーザーの情報を格納するクラス
 */
class LoginUser (user: User): org.springframework.security.core.userdetails.User(
        user.email,
        PasswordEncoderFactories.createDelegatingPasswordEncoder().encode(user.pass),
        AuthorityUtils.createAuthorityList("ROLE_USER")) {
    /**
     * ログインユーザー
     */
    var loginUser: User? = null

    init{
        // スーパークラスのユーザーID、パスワードに値をセットする
        // 実際の認証はスーパークラスのユーザーID、パスワードで行われる
        this.loginUser = user
    }

}