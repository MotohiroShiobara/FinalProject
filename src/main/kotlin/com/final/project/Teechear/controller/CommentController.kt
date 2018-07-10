package com.final.project.Teechear.controller

import com.final.project.Teechear.validate.CommentForm
import org.springframework.stereotype.Controller
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/comment")
class CommentController {

    @PostMapping("/create")
    fun create(commentForm: CommentForm, bindingResult: BindingResult): String {
        return "redirect:/article/3"
    }
}