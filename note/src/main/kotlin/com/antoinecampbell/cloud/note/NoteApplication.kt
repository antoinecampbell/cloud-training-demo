package com.antoinecampbell.cloud.note

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.boot.web.servlet.FilterRegistrationBean
import org.springframework.cloud.client.discovery.EnableDiscoveryClient
import org.springframework.context.annotation.Bean
import org.springframework.web.filter.ForwardedHeaderFilter


@SpringBootApplication
@EnableDiscoveryClient
class NoteApplication {

    // Required to rewrite links in Spring 5+
    @Bean
    fun forwardedHeaderFilter(): FilterRegistrationBean<ForwardedHeaderFilter> {
        val bean = FilterRegistrationBean<ForwardedHeaderFilter>()
        bean.filter = ForwardedHeaderFilter()
        return bean
    }
}

fun main(args: Array<String>) {
    runApplication<NoteApplication>(*args)
}
