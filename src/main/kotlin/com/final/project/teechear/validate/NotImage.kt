package com.final.project.teechear.validate

import javax.validation.Constraint
import javax.validation.Payload
import kotlin.reflect.KClass

@Target(AnnotationTarget.CLASS, AnnotationTarget.FIELD, AnnotationTarget.ANNOTATION_CLASS, AnnotationTarget.TYPE_PARAMETER, AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
@Constraint(validatedBy = [NotImageValidator::class])

annotation class NotImage(
        val message: String = "png, jpg, jpegのみ利用することができます",
        val groups: Array<KClass<*>> = [],
        val payload: Array<KClass<out Payload>> = []
)