package com.final.project.teechear.controller

import com.final.project.teechear.entity.CommentEntity
import com.final.project.teechear.mapper.CommentMapper
import com.final.project.teechear.form.CommentForm
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
        if (!bindingResult.hasErrors()) {
            commentMapper.insert(CommentEntity(commentForm.userId, commentForm.articleId, commentForm.text))
        }

        return "redirect:/article/${commentForm.articleId}"
    }
}