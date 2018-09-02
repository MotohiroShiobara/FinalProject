package com.final.project.teechear

import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.test.context.junit4.SpringRunner

@Configuration
@Profile("test")
@RunWith(SpringRunner::class)
@SpringBootTest
class TeechearApplicationTests {

	@Test
	fun contextLoads() {
	}

}
