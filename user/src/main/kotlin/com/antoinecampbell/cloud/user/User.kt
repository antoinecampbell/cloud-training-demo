package com.antoinecampbell.cloud.user

import com.fasterxml.jackson.annotation.JsonProperty
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.core.authority.AuthorityUtils
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.provisioning.UserDetailsManager
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import java.io.Serializable
import java.security.Principal
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.IdClass
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.Table
import javax.persistence.UniqueConstraint
import javax.validation.Valid
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size


@Entity
@Table(name = "users")
data class User(
    @Id
    @Column(name = "username", nullable = false, length = 50)
    @NotNull
    @Size(min = 4, max = 50)
    var username: String? = null,

    @Column(name = "password", nullable = false, length = 500)
    @NotNull
    @Size(min = 4, max = 500)
    var password: String? = null,

    @Column(name = "enabled", nullable = false)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    var enabled: Boolean? = true) : Serializable

@Entity
@Table(name = "authorities", uniqueConstraints = [UniqueConstraint(columnNames = ["username", "authority"])])
@IdClass(Authority.Key::class)
class Authority(
    @Id
    @ManyToOne
    @JoinColumn(name = "username", nullable = false)
    var user: User? = null,

    @Id
    @Column(name = "authority", nullable = false, length = 50)
    var authority: String? = null) {

    class Key : Serializable {
        private val user: User? = null
        private val authority: String? = null
    }
}

enum class SecurityRoles private constructor(val role: String) {
    USER("ROLE_USER"),
    ADMIN("ROLE_ADMIN");

    override fun toString(): String {
        return role
    }
}

@RestController
@RequestMapping("/users")
class UserController @Autowired
constructor(private val userDetailsManager: UserDetailsManager,
            private val passwordEncoder: PasswordEncoder) {

    @RequestMapping(method = [RequestMethod.GET])
    fun getUser(@AuthenticationPrincipal principal: Principal): Principal {
        return principal
    }

    @RequestMapping(method = [RequestMethod.POST], consumes = [MediaType.APPLICATION_JSON_VALUE])
    @Transactional
    fun createUser(@RequestBody @Valid user: User): ResponseEntity<Any> {
        if (userDetailsManager.userExists(user.username)) {
            throw UserNameExistException(user.username)
        }
        val userDetails = org.springframework.security.core.userdetails.User(
            user.username!!,
            passwordEncoder.encode(user.password),
            AuthorityUtils.createAuthorityList(SecurityRoles.USER.role))
        userDetailsManager.createUser(userDetails)

        return ResponseEntity.status(HttpStatus.CREATED).build()
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    private class UserNameExistException(username: String?) :
        RuntimeException(String.format("The username %s is already taken", username))
}
