package com.final.project.Teechear.controller

import com.amazonaws.AmazonClientException
import com.amazonaws.AmazonServiceException
import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.AmazonS3Client
import com.amazonaws.services.s3.model.ObjectMetadata
import com.amazonaws.services.s3.model.PutObjectRequest
import com.final.project.Teechear.domain.Lesson
import com.final.project.Teechear.domain.User
import com.final.project.Teechear.entity.UserEntity
import com.final.project.Teechear.mapper.ArticleMapper
import com.final.project.Teechear.mapper.UserMapper
import com.final.project.Teechear.service.*
import com.final.project.Teechear.validate.UserEditForm
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
                     private val pagiNationService: PagiNationService,
                     private val s3Service: S3Service,
                     private val userService: UserService,
                     private val lessonService: LessonService) {

    private val pagePerCount = 15

    @GetMapping("/{userId}")
    fun show(
            @PathVariable("userId") userId: Int,
            model: Model,
            principal: Principal,
            @RequestParam("page") pageCount: Int?): String {
        val user = userService.select(userId)
        val currentUser = userMapper.findByEmailOrName(principal.name)
        val articleList = articleService.userArticleList(userId)
        val articleCount = articleList.size
        val contribution = articleList.sumBy { it.likeCount ?: 0 }

        val currentPage = pageCount ?: 1
        val pageList = pagiNationService.obtainPageList(currentPage, articleCount, pagePerCount)
        val lessonList: List<Lesson> = lessonService.selectByOwnerId(userId)

        model.addAttribute("currentUserId", currentUser?.id)
        model.addAttribute("user", user)
        model.addAttribute("articleList", articleList)
        model.addAttribute("contribution", contribution)
        model.addAttribute("pageCount", currentPage)
        model.addAttribute("pageList", pageList)
        model.addAttribute("lessonList", lessonList)
        return "user/show"
    }

    @GetMapping("/{userId}/edit")
    fun edit(@PathVariable("userId") userId: Int, model: Model, principal: Principal): String {
        val currentUser = obtainCurrentUser(principal.name)
        if (userId != currentUser?.id) {
            return "redirect:/trend"
        }

        model.addAttribute("userEditForm", UserEditForm(currentUser.accountName, currentUser.profile))
        return "user/edit"
    }

    @PostMapping("")
    fun update(@Validated userEditForm: UserEditForm, bindingResult: BindingResult, principal: Principal): String {
        if (bindingResult.hasErrors()) {
            return "user/edit"
        }

        val multipartFile = userEditForm.iconImageUrl
        val currentUser = obtainCurrentUser(principal.name)

        val url = if (multipartFile is MultipartFile) {
            if (multipartFile.isEmpty) {
                if (currentUser?.iconImageUrl is String) currentUser.iconImageUrl else String()
            } else {
                s3Service.upload(multipartFile)
            }
        } else {
            if (currentUser?.iconImageUrl is String) currentUser.iconImageUrl else ""
        }

        val copyCurrentUser = currentUser?.copy(accountName = userEditForm.accountName, profile = userEditForm.profile, iconImageUrl = url)
        userMapper.update(copyCurrentUser!!)
        return "redirect:user/${currentUser.id}"
    }

    private fun obtainCurrentUser(accountName: String): UserEntity? {
        return userMapper.findByEmailOrName(accountName)
    }
}

