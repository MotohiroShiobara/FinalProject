package com.final.project.teechear.service

import com.final.project.teechear.entity.LessonEntity
import com.final.project.teechear.entity.UserEntity
import com.final.project.teechear.mapper.LessonMapper
import com.final.project.teechear.mapper.UserMapper
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner
import java.util.*
import javax.transaction.Transactional

@SpringBootTest
@RunWith(SpringRunner::class)
@Transactional
class LessonServiceTest {

    @Autowired
    private lateinit var lessonService: LessonService

    @Autowired
    private lateinit var lessonMapper: LessonMapper

    @Autowired
    private lateinit var userMapper: UserMapper

    @Test
    fun select() {
        // 事前準備

        // User
        val userEntity = UserEntity(
                accountName = "accountName",
                password = "password",
                emailAddress = "email@address"
        )
        userMapper.insert(userEntity)

        // Lesson
        val lessonEntity = LessonEntity(
                title = "title",
                eventDatetime = Date(),
                description = "description",
                emailAddress = "email@address",
                ownerId = userEntity.id,
                price = 0,
                isOpen = true)
        lessonMapper.insert(lessonEntity)

        // 条件
        val expectedId: Int = lessonEntity.id!!
        val result = lessonService.select(expectedId)

        // 結果
        Assert.assertEquals(expectedId, result.id)
    }
}