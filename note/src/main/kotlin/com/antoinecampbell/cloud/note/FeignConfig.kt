package com.antoinecampbell.cloud.note

import feign.form.FormEncoder
import org.springframework.beans.factory.ObjectFactory
import org.springframework.boot.autoconfigure.http.HttpMessageConverters
import org.springframework.cloud.openfeign.support.SpringEncoder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration


@Configuration
class FeignConfig(private val messageConverters: ObjectFactory<HttpMessageConverters>) {

    @Bean
    fun feignFormEncoder(): FormEncoder {
        return FormEncoder(SpringEncoder(this.messageConverters))
    }
}
