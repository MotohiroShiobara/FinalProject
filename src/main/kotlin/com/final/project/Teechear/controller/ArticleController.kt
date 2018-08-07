package com.final.project.Teechear.controller

import com.final.project.Teechear.domain.Comment
import com.final.project.Teechear.domain.CreateArticle
import com.final.project.Teechear.domain.Lesson
import com.final.project.Teechear.domain.UpdateArticle
import com.final.project.Teechear.entity.ArticleEntity
import com.final.project.Teechear.entity.UserLikeArticleEntity
import com.final.project.Teechear.exception.ResourceNotFoundException
import com.final.project.Teechear.form.ArticleForm
import com.final.project.Teechear.form.CommentForm
import com.final.project.Teechear.helper.AlertMessage
import com.final.project.Teechear.helper.AlertMessageType
import com.final.project.Teechear.mapper.ArticleMapper
import com.final.project.Teechear.mapper.UserLikeArticleMapper
import com.final.project.Teechear.mapper.UserMapper
import com.final.project.Teechear.service.*
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.BindingResult
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.mvc.support.RedirectAttributes
import java.security.Principal
import java.sql.SQLException
import java.util.*

@Controller
@RequestMapping("/article")
class ArticleController(
        private val userMapper: UserMapper,
        private val userService: UserService,
        private val articleMapper: ArticleMapper,
        private val userLikeArticleMapper: UserLikeArticleMapper,
        private val likeService: LikeService,
        private val articleService: ArticleService,
        private val commentService: CommentService,
        private val lessonService: LessonService) {

    @GetMapping("/new")
    fun new(model: Model, principal: Principal): String {
        val currentUserId = userMapper.findByEmailOrName(principal.name)
        model.addAttribute("currentUserId", currentUserId)
        model.addAttribute("articleForm", ArticleForm())
        return "article/new"
    }

    @GetMapping("/{articleId}/edit")
    fun edit(model: Model, principal: Principal, @PathVariable("articleId") articleId: Int): String {
        val currentUserId = userService.currentUser(principal).id
        val article = articleService.find(articleId)
        model.addAttribute("jsMarkdownText", article.markdownText)
        // もし記事の投稿者ではない場合はリダイレクトする
        if (currentUserId != article.userId) {
            return "error/404.html"
        }

        model.addAttribute("articleForm", ArticleForm(article.title, article.markdownText))
        model.addAttribute("articleId", articleId)
        return "article/edit"
    }

    // TODO CreateArticleを作る
    // TODO ArticleService.createを作る
    // TODO エラーハンドリングをする
    @PostMapping("")
    fun create(
            principal: Principal,
            @Validated articleForm: ArticleForm,
            result: BindingResult
    ): String {
        if (result.hasErrors()) {
            return "article/new"
        }

        val currentUser = userService.currentUser(principal)
        val createArticle = CreateArticle(articleForm.title, articleForm.markdownText, currentUser.id)
        val articleId = articleService.create(createArticle)
        return "redirect:/article/${articleId}"
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
        val article = try {
            articleService.find(articleId)
        } catch (e: ArticleService.ArticleServiceException) {
            return "error/404.html"
        } catch (e: ArticleService.ArticleNotFoundException) {
            return "error/404.html"
        }

        val currentUser = userService.currentUser(principal)
        val currentUserId = currentUser.id

        model.addAttribute("likeCount", userLikeArticleMapper.articleLikeCount(articleId))
        model.addAttribute("commentForm", commentForm)
        model.addAttribute("currentUserId", currentUserId)

        val commentList = commentService.commentListByArticle(articleId)
        model.addAttribute("commentList", commentList)
        model.addAttribute("userLiked", userLikeArticleMapper.findByUserIdAndArticleId(articleId, currentUserId) is UserLikeArticleEntity)

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
        likeService.create(articleId, principal)

        return "redirect:/article/$articleId"
    }

    @DeleteMapping("/{articleId}/unlike")
    fun unlike(@PathVariable("articleId") articleId: Int, principal: Principal): String {
        val user = userService.currentUser(principal)
        likeService.delete(articleId, user)
        return "redirect:/article/$articleId"
    }

    @DeleteMapping("/{articleId}")
    fun delete(@PathVariable("articleId") articleId: Int, principal: Principal, redirectAttributes: RedirectAttributes): String {
        val user = userService.currentUser(principal)
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