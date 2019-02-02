package com.antoinecampbell.cloud.note

import javax.validation.ConstraintViolation

class ErrorMessage(val message: String?, errors: Set<ConstraintViolation<*>>) {
    var errors: MutableList<Error>? = null

    init {
        setErrors(errors)
    }

    private fun setErrors(errors: Set<ConstraintViolation<*>>) {
        this.errors = mutableListOf()
        errors.forEach { constraintViolation ->
            val path = constraintViolation.propertyPath.toString()
            this.errors!!.add(Error(path, constraintViolation.message))
        }
    }

    class Error(val field: String? = null, val message: String? = null)
}
