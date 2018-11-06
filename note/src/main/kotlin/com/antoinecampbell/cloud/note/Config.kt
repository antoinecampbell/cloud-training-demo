//package com.antoinecampbell.cloud.note
//
//import org.springframework.context.annotation.Bean
//import org.springframework.context.annotation.Configuration
//import org.springframework.data.domain.AuditorAware
//import org.springframework.data.jpa.repository.config.EnableJpaAuditing
//import org.springframework.security.core.context.SecurityContextHolder
//import java.util.Optional
//
//
//@Configuration // TODO: replace this when security is added
//class Config {
//
//    @Configuration
//    @EnableJpaAuditing
//    class JpaConfig {
//
//        @Bean
//        fun auditorAware(): AuditorAware<String> {
//            return AuditorAware<String> { Optional.ofNullable(SecurityContextHolder.getContext().authentication.name) }
//        }
//
//    }
//}
