package com.final.project.Teechear.controller

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping

@Controller
class TopController {

    @GetMapping("/trend")
    fun trend(): String {
        return "top/trend"
    }
}