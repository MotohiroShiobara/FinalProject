package com.final.project.teechear.controller

import com.final.project.teechear.domain.UpdateComment
import com.final.project.teechear.entity.CommentEntity
import com.final.project.teechear.exception.ResourceNotFoundException
import com.final.project.teechear.mapper.CommentMapper
import com.final.project.teechear.form.CommentForm
import com.final.project.teechear.helper.AlertMessage
import com.final.project.teechear.helper.AlertMessageType
import com.final.project.teechear.service.CommentService
import org.springframework.stereotype.Controller
import org.springframework.validation.BindingResult
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.servlet.mvc.support.RedirectAttributes
import java.security.Principal
import java.sql.SQLException

@Controller
@RequestMapping("/comment")
class CommentController(private val commentMapper: CommentMapper, private val commentService: CommentService) {

    @PostMapping("/create")
    fun create(@Validated commentForm: CommentForm, bindingResult: BindingResult): String {
        if (!bindingResult.hasErrors()) {
            commentMapper.insert(CommentEntity(commentForm.userId, commentForm.articleId, commentForm.text))
        }

        return "redirect:/article/${commentForm.articleId}"
    }

    @PatchMapping("/{id}/update")
    fun update(@PathVariable("id") id: Int,
               @Validated commentForm: CommentForm,
               bindingResult: BindingResult,
               principal: Principal,
               redirectAttributes: RedirectAttributes): String {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute(
                    "alertMessage",
                    AlertMessage(
                            message = "コメントを空で入力することはできません。",
                            type = AlertMessageType.DANGER
                    )
            )

            return "redirect:/article/${commentForm.articleId}"
        }

        try {
            commentService.update(UpdateComment(id, commentForm.text!!, commentForm.userId!!, commentForm.articleId!!))
        } catch (e: ResourceNotFoundException) {
            return "error/404.html"
        } catch (e: SQLException) {
            redirectAttributes.addFlashAttribute(
                    "alertMessage",
                    AlertMessage(
                            message = "コメントの更新ができませんでした。時間を置いて再度更新をお願いします。",
                            type = AlertMessageType.DANGER
                    )
            )
            return "redirect:/article/${commentForm.articleId}"
        }

        return "redirect:/article/${commentForm.articleId}"
    }
}