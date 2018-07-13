package com.final.project.Teechear.config

import com.final.project.Teechear.intercepter.MainIntercepter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.handler.MappedInterceptor

@Configuration
class WebMvcConfig {
    @Bean
    fun mainInterceptor(): MainIntercepter {
        return MainIntercepter()
    }

    @Bean
    fun interceptor(): MappedInterceptor {
        return MappedInterceptor(arrayOf("/**"), mainInterceptor())
    }
}
