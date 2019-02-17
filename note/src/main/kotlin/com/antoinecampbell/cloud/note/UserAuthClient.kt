package com.antoinecampbell.cloud.note

import com.fasterxml.jackson.databind.JsonNode
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.http.HttpHeaders
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod

@FeignClient("user-service")
interface UserAuthClient {

    @RequestMapping(path = ["/oauth/token"], method = [RequestMethod.POST],
        consumes = ["application/x-www-form-urlencoded"],
        headers = ["Content-Type=application/x-www-form-urlencoded"])
    fun requestToken(params: Map<String, Any>,
                     @RequestHeader(HttpHeaders.AUTHORIZATION) authHeaderValue: String): JsonNode

    @RequestMapping(path = ["/oauth/check_token"], method = [RequestMethod.POST],
        consumes = ["application/x-www-form-urlencoded"],
        headers = ["Content-Type=application/x-www-form-urlencoded"])
    fun checkToken(params: Map<String, Any>,
                   @RequestHeader(HttpHeaders.AUTHORIZATION) authHeaderValue: String): JsonNode
}
