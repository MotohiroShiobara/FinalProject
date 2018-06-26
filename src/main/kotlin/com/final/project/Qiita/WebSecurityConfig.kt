package com.final.project.Qiita

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.provisioning.InMemoryUserDetailsManager
import org.springframework.security.web.util.matcher.AntPathRequestMatcher

@Configuration
@EnableWebSecurity
class WebSecurityConfig : WebSecurityConfigurerAdapter() {
    @Throws(Exception::class)
    override fun configure(http: HttpSecurity) {
        http.authorizeRequests()
                // アクセス権限の設定
                // staticディレクトリにある、'/css/','fonts','/js/'は制限なし
                .antMatchers("/css/**", "/fonts/**", "/js/**").permitAll()
                // '/admin/'で始まるURLには、'ADMIN'ロールのみアクセス可
                .antMatchers("/admin/**").hasRole("ADMIN")
                // 他は制限なし
                .anyRequest().authenticated()
                .and()
                // ログイン処理の設定
                .formLogin()
                // ログイン処理のURL
                .loginPage("/login")
                // usernameのパラメタ名
                .usernameParameter("username")
                // passwordのパラメタ名
                .passwordParameter("password")
                .permitAll()
                .and()
                // ログアウト処理の設定
                .logout()
                // ログアウト処理のURL
                .logoutRequestMatcher(AntPathRequestMatcher("/logout"))
                // ログアウト成功時の遷移先URL
                .logoutSuccessUrl("/login")
                // ログアウト時に削除するクッキー名
                .deleteCookies("JSESSIONID")
                // ログアウト時のセッション破棄を有効化
                .invalidateHttpSession(true)
                .permitAll()
    }

    @Bean
    public override fun userDetailsService(): UserDetailsService {
        val user = User.withDefaultPasswordEncoder()
                .username("user")
                .password("password")
                .roles("USER")
                .build()

        return InMemoryUserDetailsManager(user)
    }
}