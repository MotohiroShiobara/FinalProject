package com.final.project.Teechear.intercepter

import com.final.project.Teechear.auth.model.LoginUser
import com.final.project.Teechear.entity.UserEntity
import com.final.project.Teechear.mapper.UserMapper
import com.final.project.Teechear.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.servlet.HandlerInterceptor
import org.springframework.web.servlet.ModelAndView
import org.springframework.web.servlet.resource.ResourceHttpRequestHandler
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


@Component
class MainIntercepter : HandlerInterceptor {

    @Autowired
    private val userService: UserService? = null

    @Autowired
    private val userMapper: UserMapper? = null

    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        //静的リソースの場合は認証不要
        if (handler is ResourceHttpRequestHandler) {
            return true
        }

        val authentication = SecurityContextHolder.getContext().authentication
        val principal = authentication.principal as? LoginUser
        if (principal is LoginUser) {
            // すでにログイン済みの場合はTopページにリダイレクトさせる
            if (listOf("/login", "/", "/register", "").contains(request.requestURI)) {
                response.sendRedirect("/trend")
                return false
            }
        }

        return true
    }

    override fun postHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any, model: ModelAndView?) {
        //静的リソースの場合は認証不要
        if (handler is ResourceHttpRequestHandler) {
        } else {
            val authentication = SecurityContextHolder.getContext().authentication
            val principal = authentication.principal as? LoginUser
            if (principal is LoginUser) {
                val loginUser = principal.loginUser
                if (loginUser is UserEntity && loginUser.id is Int) {
                    val userEntity = userMapper?.select(loginUser.id)
                    val userDomain = userService?.toDomain(userEntity)
                    model?.modelMap?.addAttribute("currentUser", userDomain)
                }
            }
        }
    }


    override fun afterCompletion(request: HttpServletRequest, response: HttpServletResponse, dataObject: Any, e: Exception?) {
    }
}