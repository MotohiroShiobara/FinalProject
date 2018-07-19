package com.final.project.Teechear.controller

import com.final.project.Teechear.domain.Lesson
import com.final.project.Teechear.service.LessonService
import com.final.project.Teechear.service.S3Service
import com.final.project.Teechear.service.UserService
import com.final.project.Teechear.validate.LessonNewForm
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.BindingResult
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.multipart.MultipartFile
import java.security.Principal

@Controller
@RequestMapping("/lesson")
class LessonController(
        private val lessonService: LessonService,
        private val userService: UserService,
        private val s3Service: S3Service) {

    @GetMapping("new")
    fun new(lessonNewForm: LessonNewForm, model: Model):String {
        model.addAttribute("lessonNewForm", lessonNewForm)

        return "lesson/new"
    }

    @PostMapping("create")
    fun create(@Validated form: LessonNewForm, result: BindingResult, model: Model, principal: Principal): String {
        val userId = userService.currentUser(principal).id
        val multipartFile = form.multipartFile
        val imageUrl = if (multipartFile is MultipartFile && !multipartFile.isEmpty) {
            s3Service.upload(multipartFile)
        } else {
            ""
        }

        val lessonId = lessonService.createByForm(form, userId, imageUrl)
        return "redirect:/lesson/${lessonId}"
    }

    @GetMapping("/{id}")
    fun show(@PathVariable("id") id: Int, model: Model): String {
        val lesson: Lesson = lessonService.select(id)
        model.addAttribute("lesson", lesson)
        return "lesson/show"
    }

    @PostMapping("/{id}/apply")
    fun apply(@PathVariable("id") id: Int): String {
        return "redirect:/lesson/${id}"
    }
}