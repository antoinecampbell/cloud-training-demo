package com.antoinecampbell.cloud.note

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter
import org.springframework.security.oauth2.provider.token.TokenStore
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore

@Configuration
class OAuthConfig(@Value("\${cloud.demo.public-key:}") private val publicKeyText: String) {

    @Bean
    fun tokenStore(): TokenStore {
        return JwtTokenStore(accessTokenConverter())
    }

    @Bean
    fun accessTokenConverter(): JwtAccessTokenConverter {
        val jwtAccessTokenConverter = JwtAccessTokenConverter()
        jwtAccessTokenConverter.setVerifierKey(publicKeyText)

        return jwtAccessTokenConverter
    }

    @Configuration
    @EnableResourceServer
    class ResourceServerConfiguration : ResourceServerConfigurerAdapter() {

        override fun configure(http: HttpSecurity) {
            http.authorizeRequests().antMatchers("/oauth/**").anonymous()
                .and()
                .authorizeRequests().antMatchers(HttpMethod.POST, "/users").anonymous()
                .and()
                .authorizeRequests().anyRequest().authenticated()
        }
    }
}
