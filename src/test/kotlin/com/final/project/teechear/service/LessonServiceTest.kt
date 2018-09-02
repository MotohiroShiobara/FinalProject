package com.final.project.teechear.service

import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.context.annotation.PropertySource
import org.springframework.test.context.junit4.SpringRunner

@SpringBootTest
@RunWith(SpringRunner::class)
class LessonServiceTest {

    @Autowired
    private lateinit var lessonService: LessonService

    @Test
    fun select() {
        val id = 1
        val expected = id
        val result = lessonService.select(id)
        println(result)

        Assert.assertEquals(expected, result.id)
    }
}