package com.antoinecampbell.cloud.user

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.rest.webmvc.RepositoryLinksResource
import org.springframework.hateoas.ResourceProcessor
import org.springframework.hateoas.mvc.ControllerLinkBuilder
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
import java.security.Principal
import javax.validation.Valid

@RestController
@RequestMapping("/users")
class UserController @Autowired
constructor(private val userDetailsManager: UserDetailsManager,
            private val passwordEncoder: PasswordEncoder) : ResourceProcessor<RepositoryLinksResource> {

    @RequestMapping(method = [RequestMethod.GET])
    fun getUser(@AuthenticationPrincipal principal: Principal): Principal {
        return principal
    }

    @RequestMapping(method = [RequestMethod.POST], consumes = [MediaType.APPLICATION_JSON_VALUE])
    @Transactional
    fun createUser(@Valid @RequestBody user: User): ResponseEntity<Any> {
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

    override fun process(resource: RepositoryLinksResource): RepositoryLinksResource {
        resource.add(ControllerLinkBuilder.linkTo(UserController::class.java).withRel("users"))
        return resource
    }
}
