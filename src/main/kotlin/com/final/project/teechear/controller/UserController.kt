package com.final.project.teechear.controller

import com.final.project.teechear.domain.Lesson
import com.final.project.teechear.mapper.UserMapper
import com.final.project.teechear.service.*
import com.final.project.teechear.form.UserEditForm
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.BindingResult
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import java.security.Principal

@Controller
@RequestMapping("/user")
class UserController(private val userMapper: UserMapper,
                     private val articleService: ArticleService,
                     private val s3Service: S3Service,
                     private val userService: UserService,
                     private val lessonService: LessonService,
                     private val userApplyLessonService: UserApplyLessonService) {

    @GetMapping("/{userId}")
    fun show(
            @PathVariable("userId") userId: Int,
            model: Model,
            principal: Principal): String {
        val user = try {
            userService.select(userId)
        } catch (e: UserService.UserServiceException) {
            return "error/404.html"
        } catch (e: UserService.UserNotFoundException) {
            return "error/404.html"
        }

        val currentUser = userMapper.findByEmailOrName(principal.name)
        val articleList = articleService.userArticleList(userId)
        val contribution = articleList.sumBy { it.likeCount ?: 0 }

        val userLesson = lessonService.selectByOwnerId(userId)
        val applyUserList = if (userLesson.isEmpty()) {
            emptyList()
        } else {
            userApplyLessonService.selectByLessonIds(userLesson.map { it.id })
        }

        val openLessonList: List<Lesson> = lessonService.openByOwnerId(userId)
        val closedLessonList: List<Lesson> = lessonService.closeByOwnerId(userId)
        val applyedLessonList: List<Lesson> = lessonService.selectByApplyedUserId(userId)

        model.addAttribute("currentUserId", currentUser?.id)
        model.addAttribute("user", user)
        model.addAttribute("applyUserList", applyUserList)
        model.addAttribute("articleList", articleList)
        model.addAttribute("contribution", contribution)
        model.addAttribute("openLessonList", openLessonList)
        model.addAttribute("closedLessonList", closedLessonList)
        model.addAttribute("applyedLessonList", applyedLessonList)

        return "user/show"
    }

    @GetMapping("/{userId}/edit")
    fun edit(@PathVariable("userId") userId: Int, model: Model, principal: Principal): String {
        val currentUser = try {
            userService.currentUser(principal)
        } catch (e: UserService.UserServiceException) {
            return "error/404.html"
        } catch (e: UserService.UserNotFoundException) {
            return "error/404.html"
        }

        // 編集するユーザーとログインユーザーが違う場合はリダイレクト
        if (userId != currentUser.id) {
            return "redirect:/trend"
        }

        model.addAttribute("userEditForm", UserEditForm(currentUser.accountName, currentUser.profile))
        return "user/edit"
    }

    @PostMapping("")
    fun update(@Validated userEditForm: UserEditForm, bindingResult: BindingResult, principal: Principal): String {
        val multipartFile = userEditForm.iconImageUrl
        val currentUser = userService.currentUser(principal)

        if (bindingResult.hasErrors()) {
            return "user/edit"
        }

        val url = if (multipartFile is MultipartFile && !multipartFile.isEmpty) {
            s3Service.imageUpload(multipartFile)
        } else {
            currentUser.iconImageUrl
        }

        userService.update(currentUser.id, userEditForm, iconImageUrl = url)
        return "redirect:user/${currentUser.id}"
    }
}
