package com.antoinecampbell.cloud.note

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.UrlBasedCorsConfigurationSource

@Configuration
class SecurityConfig {

    @Configuration
    @EnableWebSecurity
    class WebSecurityConfig @Autowired
    constructor() : WebSecurityConfigurerAdapter() {

        @Throws(Exception::class)
        override fun configure(http: HttpSecurity) {
            val source = UrlBasedCorsConfigurationSource()
            val config = CorsConfiguration()
                .apply {
                    addAllowedOrigin("*")
                    addAllowedHeader("*")
                    addAllowedMethod("*")
                    maxAge = 3600L
                }
            source.registerCorsConfiguration("/**", config)

            http.csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .cors().configurationSource(source)
                .and()
                .authorizeRequests().anyRequest().authenticated()
        }
    }

    @Configuration
    @EnableGlobalMethodSecurity(prePostEnabled = true)
    class GlobalMethodSecurityConfig : GlobalMethodSecurityConfiguration()
}
