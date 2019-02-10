package com.antoinecampbell.cloud.note

import com.fasterxml.jackson.databind.JsonNode
import org.springframework.beans.factory.annotation.Value
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class UserAuthController(private val userAuthClient: UserAuthClient,
                         @Value("\${cloud.demo.user-service-auth-header-value}")
                         private val authHeaderValue: String) {

    @RequestMapping(path = ["/oauth/token"], method = [RequestMethod.POST])
    fun requestToken(@RequestParam("username") username: String,
                     @RequestParam("password") password: String,
                     @RequestParam("grant_type") grantType: String): JsonNode {
        val paramMap = mapOf(
            Pair("username", username),
            Pair("password", password),
            Pair("grant_type", grantType))

        return userAuthClient.requestToken(paramMap, authHeaderValue)
    }

    @RequestMapping(path = ["/oauth/check_token"], method = [RequestMethod.POST])
    fun checkToken(@RequestParam("token") token: String): JsonNode {
        val paramMap = mapOf(Pair("token", token))

        return userAuthClient.checkToken(paramMap, authHeaderValue)
    }
}
