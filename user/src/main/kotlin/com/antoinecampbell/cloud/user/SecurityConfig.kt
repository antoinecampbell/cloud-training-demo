package com.antoinecampbell.cloud.user

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.data.repository.query.SecurityEvaluationContextExtension
import org.springframework.security.provisioning.JdbcUserDetailsManager
import org.springframework.security.provisioning.UserDetailsManager
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.UrlBasedCorsConfigurationSource
import javax.sql.DataSource


@Configuration
class SecurityConfig {

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }

    @Configuration
    @EnableWebSecurity
    class WebSecurityConfig @Autowired
    constructor(private val dataSource: DataSource,
                private val passwordEncoder: PasswordEncoder) : WebSecurityConfigurerAdapter() {

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
        }

        @Throws(Exception::class)
        override fun configure(auth: AuthenticationManagerBuilder) {
            auth.userDetailsService(userDetailsManager())
                .passwordEncoder(passwordEncoder)
        }

        @Bean
        fun userDetailsManager(): UserDetailsManager {
            val userDetailsManager = JdbcUserDetailsManager(dataSource)
            userDetailsManager.setDataSource(dataSource)
            return userDetailsManager
        }

        @Bean
        fun securityEvaluationContextExtension(): SecurityEvaluationContextExtension {
            return SecurityEvaluationContextExtension()
        }

        @Bean
        override fun authenticationManagerBean(): AuthenticationManager {
            return super.authenticationManagerBean()
        }

    }

    @Configuration
    @EnableGlobalMethodSecurity(prePostEnabled = true)
    class GlobalMethodSecurityConfig : GlobalMethodSecurityConfiguration()
}
