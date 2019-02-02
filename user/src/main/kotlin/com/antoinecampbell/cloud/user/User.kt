package com.antoinecampbell.cloud.user

import javax.validation.constraints.NotNull
import javax.validation.constraints.Size


data class User(
    @get:NotNull
    @get:Size(min = 4, max = 50)
    var username: String? = null,

    @get:NotNull
    @get:Size(min = 4, max = 500)
    var password: String? = null)
