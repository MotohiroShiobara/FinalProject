package com.final.project.Qiitq.UserDetail

import com.final.project.Qiitq.domain.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.GrantedAuthority



class UserWithUserDetail(private val user: User) : UserDetails {

    override fun getAuthorities(): Collection<GrantedAuthority>? {
        // TODO 自動生成されたメソッド・スタブ
        return null
    }

    override fun getPassword(): String {
        // TODO 自動生成されたメソッド・スタブ
        return this.user.password
    }

    override fun getUsername(): String {
        // TODO 自動生成されたメソッド・スタブ
        return this.user.name
    }

    override fun isAccountNonExpired(): Boolean {
        // TODO 自動生成されたメソッド・スタブ
        return true
    }

    override fun isAccountNonLocked(): Boolean {
        // TODO 自動生成されたメソッド・スタブ
        return true
    }

    override fun isCredentialsNonExpired(): Boolean {
        // TODO 自動生成されたメソッド・スタブ
        return true
    }

    override fun isEnabled(): Boolean {
        // TODO 自動生成されたメソッド・スタブ
        return true
    }
}