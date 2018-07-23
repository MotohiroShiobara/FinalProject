package com.final.project.Teechear.controller

import com.final.project.Teechear.entity.CommentEntity
import com.final.project.Teechear.mapper.CommentMapper
import com.final.project.Teechear.form.CommentForm
import org.springframework.stereotype.Controller
import org.springframework.validation.BindingResult
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/comment")
class CommentController(private val commentMapper: CommentMapper) {

    @PostMapping("/create")
    fun create(@Validated commentForm: CommentForm, bindingResult: BindingResult): String {
        commentMapper.insert(CommentEntity(commentForm.userId, commentForm.articleId, "\n" + commentForm.text))
        return "redirect:/article/${commentForm.articleId}"
    }
}