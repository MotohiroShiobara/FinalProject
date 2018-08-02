
package com.final.project.Teechear.validate

import org.springframework.web.multipart.MultipartFile
import javax.validation.ConstraintValidator
import javax.validation.ConstraintValidatorContext

class NotImageValidator: ConstraintValidator<NotImage, MultipartFile> {

    private var message: String = ""

    override fun initialize(constraintAnnotation: NotImage?) {
        if (constraintAnnotation == null) return
        message = constraintAnnotation.message
    }

    override fun isValid(multipartFile: MultipartFile?, context: ConstraintValidatorContext?): Boolean {
        if (multipartFile is MultipartFile && !multipartFile.isEmpty) {
            return isImageConetntType(multipartFile.contentType)
        }

        return true
    }

    private fun isImageConetntType(contentType: String?): Boolean {
        when (contentType) {
            "image/png", "image/jpeg", "image/jpg" -> return true
            else -> return false
        }
    }
}
