package com.final.project.teechear.service

import org.junit.Test
import org.junit.Assert.assertEquals
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner
import java.util.*

@SpringBootTest
@RunWith(SpringRunner::class)
class DateTimeServiceTest {

    @Autowired
    private lateinit var dateTimeService: DateTimeService

    @Test
    fun toDate() {
        val strDate = "2018-08-29T12:25"
        val expected = Date(2018, 8, 29, 12, 25)

        val result = dateTimeService.toDate(strDate)

        assertEquals(result, expected)
    }
}