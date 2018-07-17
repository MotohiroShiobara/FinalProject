package com.final.project.Teechear.auth.config

import com.final.project.Teechear.auth.handler.AuthenticationFailureHandler
import com.final.project.Teechear.auth.service.UserDetailsServiceImpl
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.authentication.configuration.GlobalAuthenticationConfigurerAdapter
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.util.matcher.AntPathRequestMatcher

/**
 * Spring Security設定クラス.
 */
@Configuration
@EnableWebSecurity   // Spring Securityの基本設定
open class SecurityConfig : WebSecurityConfigurerAdapter() {

    override fun configure(web : WebSecurity) {
        // ここに設定したものはセキュリティ設定を無視
        web.ignoring().antMatchers(
                "/**/favicon.ico",
                "/image/**",
                "/css/**",
                "/javascript/**",
                "/webjars/**")
    }

    override fun configure(http : HttpSecurity)  {
        // SSL許可
        //http.requiresChannel().anyRequest().requiresSecure()

        // 認可の設定
        http.authorizeRequests()
                .antMatchers("/login", "/", "/signup").permitAll() // indexは全ユーザーアクセス許可
                .anyRequest().authenticated()  // それ以外は全て認証無しの場合アクセス不許可

        // ログイン設定
        http.formLogin()
                .loginProcessingUrl("/users/login")   // 認証処理のパス
                .loginPage("/login")            // ログインフォームのパス
                .failureHandler(AuthenticationFailureHandler())       // 認証失敗時に呼ばれるハンドラクラス
                .defaultSuccessUrl("/trend")     // 認証成功時の遷移先
                .usernameParameter("loginName").passwordParameter("pass")  // ユーザー名、パスワードのパラメータ名
                .and()

        // ログアウト
        http.logout()
                .logoutRequestMatcher(AntPathRequestMatcher("/logout**"))
                .logoutSuccessUrl("/login")
    }

    @Configuration
    open class AuthenticationConfiguration : GlobalAuthenticationConfigurerAdapter() {
        @Autowired
        var userDetailsService : UserDetailsServiceImpl = UserDetailsServiceImpl()

        override fun init( auth : AuthenticationManagerBuilder) {
            // 認証するユーザーの設定
            auth.userDetailsService(userDetailsService).passwordEncoder(BCryptPasswordEncoder())
        }
    }
}
