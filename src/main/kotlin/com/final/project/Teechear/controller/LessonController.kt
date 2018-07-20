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
    fun new(model: Model):String {
        // 直近に作成したlessonがあればそのlessonのemail_addressをlessonNewFormに渡す
        model.addAttribute("lessonNewForm", LessonNewForm())
        return "lesson/new"
    }

    @PostMapping("create")
    fun create(@Validated form: LessonNewForm, result: BindingResult, model: Model, principal: Principal): String {


        val userId = userService.currentUser(principal).id
        val multipartFile = form.multipartFile
        val imageUrl = if (multipartFile is MultipartFile && !multipartFile.isEmpty) {
            try {
                s3Service.imageUpload(multipartFile)
            } catch (e : S3Service.S3ServiceNotImage) {
                ""
            }
        } else {
            ""
        }

        if (lessonService.validation(form, result).hasErrors()) {
            return "lesson/new"
        }

        val lessonId = lessonService.createByForm(form, userId, imageUrl)
        return "redirect:/lesson/${lessonId}"
    }

    @GetMapping("/{id}")
    fun show(@PathVariable("id") id: Int, model: Model, principal: Principal): String {
        val lesson: Lesson = lessonService.select(id)
        val currentUserId = userService.currentUser(principal).id
        val canApply = lessonService.canApply(lesson, currentUserId)
        model.addAttribute("canApply", canApply)
        val isApply = if (canApply) false else lessonService.isApply(lesson, currentUserId)
        model.addAttribute("isApply", isApply)
        model.addAttribute("lesson", lesson)
        return "lesson/show"
    }

    @PostMapping("/{id}/apply")
    fun apply(@PathVariable("id") id: Int, principal: Principal): String {
        val currentUserId = userService.currentUser(principal).id
        lessonService.apply(id, currentUserId)
        return "redirect:/lesson/${id}/apply_completed"
    }

    @GetMapping("/{id}/apply_completed")
    fun applyCompleted(@PathVariable("id") id: Int, principal: Principal, model: Model): String {
        val lesson = lessonService.select(id)
        model.addAttribute("lessonId", id)
        model.addAttribute("emailAddress", lesson.emailAddress)

        return "lesson/apply_completed"
    }

    @PostMapping("/{id}/close")
    fun close(@PathVariable("id") id: Int): String {
        lessonService.close(id)
        return "redirect:/lesson/${id}"
    }
}