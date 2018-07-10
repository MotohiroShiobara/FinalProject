package com.final.project.Teechear.controller

import com.final.project.Teechear.domain.Comment
import com.final.project.Teechear.mapper.CommentMapper
import com.final.project.Teechear.validate.CommentForm
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
        println(commentForm)
        commentMapper.insert(Comment(commentForm.userId, commentForm.articleId, commentForm.text))
        return "redirect:/article/3"
    }
}