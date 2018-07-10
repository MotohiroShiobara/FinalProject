//package com.final.project.Teechear
//
//import org.springframework.stereotype.Component
//import org.springframework.web.servlet.HandlerInterceptor
//import org.springframework.web.servlet.ModelAndView
//import javax.servlet.http.HttpServletRequest
//import javax.servlet.http.HttpServletResponse
//
//@Component
//class CustomIntercepter : HandlerInterceptor {
//
//    @Throws(Exception::class)
//
//    // コントローラー実行前に行いたい処理を記述する
//    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
//        return super.preHandle(request, response, handler)
//    }
//
//
//    @Throws(Exception::class)
//    override fun postHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any, modelAndView: ModelAndView?) {
//        super.postHandle(request, response, handler, modelAndView)
//    }
//
//    @Throws(Exception::class)
//    override fun afterCompletion(request: HttpServletRequest, response: HttpServletResponse, handler: Any, ex: java.lang.Exception?) {
//        super.afterCompletion(request, response, handler, ex)
//    }
//}