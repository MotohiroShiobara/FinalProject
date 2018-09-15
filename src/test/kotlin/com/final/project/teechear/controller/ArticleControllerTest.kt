package com.final.project.teechear.controller

import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest
@RunWith(SpringRunner::class)
@WebMvcTest
class ArticleControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Test
    fun new() {
        mockMvc.perform(MockMvcRequestBuilders.get("/article/new")).andExpect(status().isOk)
    }
}