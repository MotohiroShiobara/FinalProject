package com.final.project.Teechear.controller

import com.final.project.Teechear.domain.Article
import com.final.project.Teechear.domain.Comment
import com.final.project.Teechear.domain.UserLikeArticle
import com.final.project.Teechear.mapper.ArticleMapper
import com.final.project.Teechear.mapper.CommentMapper
import com.final.project.Teechear.mapper.UserLikeArticleMapper
import com.final.project.Teechear.mapper.UserMapper
import com.final.project.Teechear.service.LikeService
import com.final.project.Teechear.validate.ArticleForm
import com.final.project.Teechear.validate.CommentForm
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.BindingResult
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import java.security.Principal
import java.util.*

@Controller
@RequestMapping("/article")
class ArticleController(
        private val userMapper: UserMapper,
        private val articleMapper: ArticleMapper,
        private val commentMapper: CommentMapper,
        private val userLikeArticleMapper: UserLikeArticleMapper,
        private val likeService: LikeService) {

    @GetMapping("/new")
    fun new(model: Model, principal: Principal): String {
        val currentUserId = userMapper.findByEmailOrName(principal.name)
        model.addAttribute("currentUserId", currentUserId)
        model.addAttribute("articleForm", ArticleForm())
        return "article/new"
    }

    @PostMapping("")
    fun create(
            principal: Principal,
            @Validated articleForm: ArticleForm,
            bindingResult: BindingResult
    ): String {
        val currentUser = userMapper.findByEmailOrName(principal.name)
        val article = Article(articleForm.title, currentUser?.id, Date(), articleForm.markdownText)
        articleMapper.insert(article)
        return "redirect:/article/${article.id}"
    }

    @GetMapping("/{articleId}")
    fun show(@PathVariable("articleId") articleId: Int, model: Model, principal: Principal, commentForm: CommentForm): String {
        val article = articleMapper.find(articleId)
        val currentUser = userMapper.findByEmailOrName(principal.name)
        val currentUserId = currentUser?.id

        if (currentUserId is Int) {
            model.addAttribute("likeCount", userLikeArticleMapper.articleLikeCount(articleId))
            model.addAttribute("commentForm", commentForm)
            model.addAttribute("currentUserId", currentUserId)
            model.addAttribute("commentList", commentMapper.selectByArticleId(articleId))
            model.addAttribute("userLiked", userLikeArticleMapper.findByUserIdAndArticleId(articleId, currentUserId) is UserLikeArticle)
        }

        if (article is Article) {
            model.addAttribute("article", article)
            model.addAttribute("isMyArticle", article.userId == currentUserId)
            return "article/show"
        }

        return "error/404.html"
    }

    @GetMapping("/{articleId}/comments.json")
    fun commentJson(@PathVariable("articleId") articleId: Int): ResponseEntity<List<Comment>> {
        val commentList = commentMapper.selectByArticleId(articleId)
        return ResponseEntity.ok(commentList)
    }

    @PostMapping("/{articleId}/like")
    fun like(@PathVariable("articleId") articleId: Int, principal: Principal): String {
        likeService.create(articleId, principal)

        return "redirect:/article/${articleId}"
    }
}