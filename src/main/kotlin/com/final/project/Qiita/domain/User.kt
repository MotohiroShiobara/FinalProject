package com.final.project.Qiita.domain

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.AuthorityUtils
import org.springframework.security.core.userdetails.UserDetails


class User(val name: String, val email: String, val pass: String, val description: String, val id: Int = 0, val roleType: String = "ROLE_USER") : UserDetails {

    override fun getUsername(): String? {
        return this.name
    }

    override fun getPassword(): String? {
        return this.pass
    }

    override fun getAuthorities(): MutableCollection<out GrantedAuthority>? {
        return AuthorityUtils.createAuthorityList(this.roleType)
    }

    override fun isAccountNonExpired(): Boolean {
        return true
    }

    override fun isAccountNonLocked(): Boolean {
        return true
    }

    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    override fun isEnabled(): Boolean {
        return true
    }
}

