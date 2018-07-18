package com.final.project.Teechear.service

import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.*



@Service
class DateTimeService {

    fun toDate(localDateTime: LocalDateTime): Date {
        val ldt = LocalDateTime.now()
        val zdt = ldt.atZone(ZoneId.systemDefault())
        return Date.from(zdt.toInstant())
    }
}