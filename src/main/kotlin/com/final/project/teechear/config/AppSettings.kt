package com.final.project.teechear.config

import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.PropertySource
import org.springframework.core.env.Environment

@Configuration
@PropertySource("classpath:application.properties")
class AppSettings(private val env: Environment) {

    fun get(key: String): String {
        return env.getProperty(key) ?: "hoge"
    }
}