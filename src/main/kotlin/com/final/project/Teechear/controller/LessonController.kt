package com.final.project.Teechear.controller

import com.final.project.Teechear.validate.LessonNewForm
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/lesson")
class LessonController {

    @GetMapping("new")
    fun new(model: Model):String {
        model.addAttribute("newLessonForm", LessonNewForm())

        return "lesson/new"
    }
}