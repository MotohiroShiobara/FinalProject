package com.final.project.Teechear.controller

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/lesson")
class LessonController {

    @GetMapping("new")
    fun new():String {
        return "/lesson/new"
    }
}