package com.final.project.teechear.config

import org.springframework.boot.web.servlet.MultipartConfigFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import javax.servlet.MultipartConfigElement

@Configuration
class MultiPartConfigure {

    @Bean
    fun multipartConfigElement(): MultipartConfigElement {
        val factory = MultipartConfigFactory()
        factory.setMaxFileSize("100MB")
        factory.setMaxRequestSize("100MB")
        return factory.createMultipartConfig()
    }
}