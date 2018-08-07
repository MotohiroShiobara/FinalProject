package com.final.project.Teechear.controller

import com.final.project.Teechear.exception.ResourceNotFoundException
import com.final.project.Teechear.form.LessonNewForm
import com.final.project.Teechear.helper.AlertMessage
import com.final.project.Teechear.helper.AlertMessageType
import com.final.project.Teechear.service.LessonService
import com.final.project.Teechear.service.S3Service
import com.final.project.Teechear.service.UserApplyLessonService
import com.final.project.Teechear.service.UserService
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.BindingResult
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import org.springframework.web.servlet.mvc.support.RedirectAttributes
import java.security.Principal
import java.sql.SQLException

@Controller
@RequestMapping("/lesson")
class LessonController(
        private val lessonService: LessonService,
        private val userService: UserService,
        private val s3Service: S3Service,
        private val userApplyLessonService: UserApplyLessonService) {

    @GetMapping("new")
    fun new(model: Model): String {
        // 直近に作成したlessonがあればそのlessonのemail_addressをlessonNewFormに渡す
        model.addAttribute("lessonNewForm", LessonNewForm())
        return "lesson/new"
    }

    @PostMapping("create")
    fun create(@Validated form: LessonNewForm, result: BindingResult, model: Model, principal: Principal): String {
        if (lessonService.validation(form, result).hasErrors()) {
            return "lesson/new"
        }

        val userId = userService.currentUser(principal).id
        val multipartFile = form.multipartFile
        val imageUrl = if (multipartFile is MultipartFile && !multipartFile.isEmpty) {
            s3Service.imageUpload(multipartFile)
        } else {
            ""
        }

        val lessonId = lessonService.createByForm(form, userId, imageUrl)
        return "redirect:/lesson/${lessonId}"
    }

    @GetMapping("/{id}")
    fun show(@PathVariable("id") id: Int, model: Model, principal: Principal): String {
        val lesson = try {
            lessonService.select(id)
        } catch (e: LessonService.LessonServiceException) {
            return "error/404.html"
        } catch (e: LessonService.LessonNotFoundException) {
            return "error/404.html"
        }

        val currentUserId = userService.currentUser(principal).id
        val canApply = lessonService.canApply(lesson, currentUserId)
        model.addAttribute("canApply", canApply)
        val isApply = if (canApply) false else lessonService.isApply(lesson, currentUserId)
        model.addAttribute("isApply", isApply)
        model.addAttribute("lesson", lesson)
        model.addAttribute("isDeletable", lessonService.isDeletable(lesson, currentUserId))
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

        val lesson = try {
            lessonService.select(id)
        } catch (e: LessonService.LessonServiceException) {
            return "error/404.html"
        } catch (e: LessonService.LessonNotFoundException) {
            return "error/404.html"
        }

        // 応募済みではないユーザーはこの画面にはいけないため404画面を返す
        val currentUserId = userService.currentUser(principal).id
        if (!lessonService.isApply(lesson, currentUserId)) {
            return "error/404.html"
        }

        model.addAttribute("lessonId", id)
        model.addAttribute("emailAddress", lesson.emailAddress)

        return "lesson/apply_completed"
    }

    @PostMapping("/{id}/close")
    fun close(@PathVariable("id") id: Int): String {
        lessonService.close(id)
        return "redirect:/lesson/${id}"
    }

    @DeleteMapping("/{id}")
    fun delete(
            @PathVariable("id") id: Int,
            principal: Principal,
            redirectAttributes: RedirectAttributes): String {
        if (userApplyLessonService.hasParticipant(id)) {
            redirectAttributes.addFlashAttribute(
                    "alertMessage",
                    AlertMessage(
                            message = "申し込み済みのユーザーが存在するため削除することができません。",
                            type = AlertMessageType.DANGER
                    )
            )
            return "redirect:/lesson/$id"
        }

        val currentUserId = userService.currentUser(principal).id
        try {
            lessonService.delete(id, currentUserId)
        } catch (e: ResourceNotFoundException) {
            return "error/404.html"
        } catch (e: SQLException) {
            redirectAttributes.addFlashAttribute(
                    "alertMessage",
                    AlertMessage(
                            message = "講座の削除に失敗しました。時間を置いて再度実行をお願いします。",
                            type = AlertMessageType.DANGER
                    )
            )
            return "redirect:/lesson/$id"
        }

        redirectAttributes.addFlashAttribute(
                "alertMessage",
                AlertMessage(message = "講座を削除しました", type = AlertMessageType.SUCCESS))
        return "redirect:/user/$currentUserId"
    }
}
