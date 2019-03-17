package com.antoinecampbell.cloud.user

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer
import org.springframework.security.oauth2.provider.token.TokenStore
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore
import org.springframework.security.provisioning.UserDetailsManager
import javax.sql.DataSource


@Configuration
class OAuthConfig(@Value("\${cloud.demo.private-key:}") private val privateKeyText: String,
                  @Value("\${cloud.demo.public-key:}") private val publicKeyText: String) {

    @Bean
    fun tokenStore(): TokenStore {
        return JwtTokenStore(accessTokenConverter())
    }

    @Bean
    fun accessTokenConverter(): JwtAccessTokenConverter {
        val jwtAccessTokenConverter = JwtAccessTokenConverter()
        jwtAccessTokenConverter.setVerifierKey(publicKeyText)
        jwtAccessTokenConverter.setSigningKey(privateKeyText)

        return jwtAccessTokenConverter
    }

    @Configuration
    @EnableAuthorizationServer
    class AuthorizationServerConfiguration @Autowired
    constructor(private val authenticationManager: AuthenticationManager,
                private val userDetailsManager: UserDetailsManager,
                private val dataSource: DataSource,
                private val tokenStore: TokenStore,
                private val accessTokenConverter: JwtAccessTokenConverter)
        : AuthorizationServerConfigurerAdapter() {

        override fun configure(clients: ClientDetailsServiceConfigurer) {
            clients.jdbc(dataSource)
        }

        override fun configure(endpoints: AuthorizationServerEndpointsConfigurer) {
            endpoints.tokenStore(tokenStore)
                .authenticationManager(authenticationManager)
                .userDetailsService(userDetailsManager)
                .tokenStore(tokenStore)
                .accessTokenConverter(accessTokenConverter)
        }

        override fun configure(security: AuthorizationServerSecurityConfigurer) {
            security.checkTokenAccess("permitAll")
                .tokenKeyAccess("permitAll")
        }
    }

    @Configuration
    @EnableResourceServer
    class ResourceServerConfiguration : ResourceServerConfigurerAdapter() {

        override fun configure(http: HttpSecurity) {
            http.authorizeRequests().antMatchers(HttpMethod.POST, "/users", "/users/").anonymous()
                .and()
                .authorizeRequests().antMatchers(HttpMethod.GET, "/users", "/users/").authenticated()
                .and()
                .authorizeRequests().antMatchers("/actuator").permitAll()
                .and()
                .authorizeRequests().antMatchers("/actuator/*").permitAll()
                .and()
                .authorizeRequests().anyRequest().authenticated()
        }
    }

}
