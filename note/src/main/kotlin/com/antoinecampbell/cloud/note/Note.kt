package com.antoinecampbell.cloud.note

import org.springframework.data.annotation.CreatedBy
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import org.springframework.hateoas.Identifiable
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.EntityListeners
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Table
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

@Entity
@Table(name = "notes")
@EntityListeners(AuditingEntityListener::class)
data class Note(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    var noteId: Long? = null,

    @Column(name = "title", length = 100, nullable = false)
    @get:NotBlank
    @get:NotNull
    var title: String? = null,

    @Column(name = "description", length = 2000)
    var description: String? = null,

    @CreatedBy
    @Column(name = "owner", length = 50, nullable = false, updatable = false)
    var owner: String? = null) : Identifiable<Long> {

    override fun getId(): Long? = noteId
}
