package com.final.project.Teechear.service

import org.springframework.stereotype.Service
import java.text.SimpleDateFormat
import java.util.*





@Service
class DateTimeService {

    fun toDate(strDateTime: String): Date {
        val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm")
        return sdf.parse(strDateTime)
    }
}