package com.final.project.Teechear.controller

import com.final.project.Teechear.service.LessonService
import com.final.project.Teechear.validate.LessonNewForm
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.BindingResult
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/lesson")
class LessonController(
        private val lessonService: LessonService) {

    @GetMapping("new")
    fun new(lessonNewForm: LessonNewForm, model: Model):String {
        model.addAttribute("lessonNewForm", lessonNewForm)

        return "lesson/new"
    }

    @PostMapping("create")
    fun create(@Validated form: LessonNewForm, result: BindingResult): String {
        lessonService.createByForm(form)
        // TODO insert文の返り値にidを持たせる
        // TODO 作成したlessonのidでリダイレクトする (ex) return "redirect:/lesson/2"
        return "redirect:/trend"
    }
}