package com.final.project.Teechear.controller

import com.final.project.Teechear.domain.Comment
import com.final.project.Teechear.domain.Lesson
import com.final.project.Teechear.domain.UpdateArticle
import com.final.project.Teechear.entity.ArticleEntity
import com.final.project.Teechear.entity.UserLikeArticleEntity
import com.final.project.Teechear.exception.ResourceNotFound
import com.final.project.Teechear.form.ArticleForm
import com.final.project.Teechear.form.CommentForm
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

    @PostMapping("")
    fun create(
            principal: Principal,
            @Validated articleForm: ArticleForm,
            result: BindingResult
    ): String {
        val currentUser = userMapper.findByEmailOrName(principal.name)
        if (result.hasErrors()) {
            return "article/new"
        }

        val article = ArticleEntity(articleForm.title, currentUser?.id, Date(), articleForm.markdownText)
        articleMapper.insert(article)
        return "redirect:/article/${article.id}"
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
        if (result.hasErrors()) {
            return "article/edit"
        }

        try {
            val updateArticle = UpdateArticle(id = articleId, title = articleForm.title, markdownText = articleForm.markdownText, userId = currentUser.id)
            articleService.update(updateArticle)
            return "redirect:/article/${articleId}"
        } catch (e: ResourceNotFound) {
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

        return "redirect:/article/${articleId}"
    }

    @DeleteMapping("/{articleId}/unlike")
    fun unlike(@PathVariable("articleId") articleId: Int, principal: Principal): String {
        val user = userService.currentUser(principal)
        likeService.delete(articleId, user)
        return "redirect:/article/${articleId}"
    }
}