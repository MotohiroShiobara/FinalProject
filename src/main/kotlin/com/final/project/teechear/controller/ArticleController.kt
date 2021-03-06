package com.final.project.teechear.controller

import com.final.project.teechear.domain.Comment
import com.final.project.teechear.domain.CreateArticle
import com.final.project.teechear.domain.Lesson
import com.final.project.teechear.domain.UpdateArticle
import com.final.project.teechear.exception.ResourceNotFoundException
import com.final.project.teechear.form.ArticleForm
import com.final.project.teechear.form.CommentForm
import com.final.project.teechear.helper.AlertMessage
import com.final.project.teechear.helper.AlertMessageType
import com.final.project.teechear.service.*
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.BindingResult
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.mvc.support.RedirectAttributes
import java.security.Principal
import java.sql.SQLException

@Controller
@RequestMapping("/article")
class ArticleController(
        private val userService: UserService,
        private val articleService: ArticleService,
        private val commentService: CommentService,
        private val lessonService: LessonService,
        private val articleLikeService: ArticleLikeService) {

    @GetMapping("/new")
    fun new(model: Model, principal: Principal): String {
        val currentUserId = userService.findByEmailOrAccountName(principal.name)
        model.addAttribute("currentUserId", currentUserId)
        model.addAttribute("articleForm", ArticleForm())
        return "article/new"
    }

    @GetMapping("/{articleId}/edit")
    fun edit(model: Model, principal: Principal, @PathVariable("articleId") articleId: Int): String {
        val currentUserId = userService.currentUser(principal).id
        val article = articleService.findById(articleId) ?: return "error/404.html"
        model.addAttribute("jsMarkdownText", article.markdownText)
        // もし記事の投稿者ではない場合はリダイレクトする
        if (currentUserId != article.userId) {
            return "error/404.html"
        }

        model.addAttribute("articleForm", ArticleForm(article.title, article.markdownText))
        model.addAttribute("articleId", articleId)
        return "article/edit"
    }

    @PostMapping("")
    fun create(
            principal: Principal,
            @Validated articleForm: ArticleForm,
            result: BindingResult,
            model: Model
    ): String {
        if (result.hasErrors()) {
            return "article/new"
        }

        val currentUser = userService.currentUser(principal)
        val createArticle = CreateArticle(articleForm.title, articleForm.markdownText, currentUser.id)
        val articleId = try {
            articleService.create(createArticle)
        }  catch (e: SQLException) {
            model.addAttribute("jsAlertMessage", "更新に失敗しました。時間をおいて再度更新をお願いします。")
            return "article/new"
        }

        return "redirect:/article/$articleId"
    }

    @PatchMapping("/update/{articleId}")
    fun update(
            @PathVariable("articleId") articleId: Int,
            principal: Principal,
            @Validated articleForm: ArticleForm,
            result: BindingResult,
            redirectAttributes: RedirectAttributes,
            model: Model
    ): String {
        val currentUser = userService.currentUser(principal)
        model.addAttribute("jsMarkdownText", articleForm.markdownText)
        if (result.hasErrors()) {
            return "article/edit"
        }

        try {
            val updateArticle = UpdateArticle(id = articleId, title = articleForm.title, markdownText = articleForm.markdownText, userId = currentUser.id)
            articleService.update(updateArticle)
            return "redirect:/article/${articleId}"
        } catch (e: ResourceNotFoundException) {
            return "error/404.html"
        } catch (e: SQLException) {
            model.addAttribute("jsAlertMessage", "更新に失敗しました。時間をおいて再度更新をお願いします。")
            return "article/edit"
        }
    }

    @GetMapping("/{articleId}")
    fun show(@PathVariable("articleId") articleId: Int, model: Model, principal: Principal, commentForm: CommentForm): String {
        val article = articleService.findById(articleId) ?: return "error/404.html"
        val currentUser = userService.findByEmailOrAccountName(principal.name) ?: return "redirect:/logout"
        val currentUserId = currentUser.id

        model.addAttribute("likeCount", articleLikeService.likeCount(articleId))
        model.addAttribute("commentForm", commentForm)
        model.addAttribute("currentUserId", currentUserId)

        val commentList = commentService.commentListByArticle(articleId)
        model.addAttribute("commentList", commentList)
        model.addAttribute("userLiked", articleLikeService.isLikedByUser(articleId, currentUserId))

        model.addAttribute("article", article)
        model.addAttribute("isMyArticle", article.userId == currentUserId)

        // 記事投稿者の直近の講座
        val mostRecentLesson: Lesson? = lessonService.mostRecentLessonByUserId(article.userId)
        model.addAttribute("mostRecentLesson", mostRecentLesson)
        return "article/show"
    }

    @GetMapping("/{articleId}/comments.json")
    fun commentJson(@PathVariable("articleId") articleId: Int): ResponseEntity<List<Comment>> {
        val commentList = commentService.commentListByArticle(articleId)
        return ResponseEntity.ok(commentList)
    }

    @PostMapping("/{articleId}/like")
    fun like(@PathVariable("articleId") articleId: Int, principal: Principal): String {
        articleLikeService.create(articleId, principal)
        return "redirect:/article/$articleId"
    }

    @DeleteMapping("/{articleId}/unlike")
    fun unlike(@PathVariable("articleId") articleId: Int, principal: Principal): String {
        val user = userService.findByEmailOrAccountName(principal.name) ?: return "redirect:/logout"
        articleLikeService.delete(articleId, user)
        return "redirect:/article/$articleId"
    }

    @DeleteMapping("/{articleId}")
    fun delete(@PathVariable("articleId") articleId: Int, principal: Principal, redirectAttributes: RedirectAttributes): String {
        val user = userService.findByEmailOrAccountName(principal.name) ?: return "redirect:/logout"
        try {
            articleService.delete(articleId, user.id)
        } catch (e: ResourceNotFoundException) {
            return "/error/404.html"
        } catch (e: SQLException) {
            redirectAttributes.addFlashAttribute(
                    "alertMessage",
                    AlertMessage(
                            message = "記事の削除に失敗しました。時間を置いて再度実行をお願いします。",
                            type = AlertMessageType.DANGER
                    )
            )
            return "redirect:/article/$articleId"
        }

        redirectAttributes.addFlashAttribute(
                "alertMessage",
                AlertMessage(
                        message = "記事を削除しました。",
                        type = AlertMessageType.SUCCESS
                )
        )
        return "redirect:/user/${user.id}"
    }
}