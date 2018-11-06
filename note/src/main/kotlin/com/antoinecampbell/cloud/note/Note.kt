package com.antoinecampbell.cloud.note

import org.springframework.data.repository.CrudRepository
import org.springframework.data.rest.core.annotation.RepositoryRestResource
import org.springframework.hateoas.Identifiable
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "notes")
//@EntityListeners(AuditingEntityListener::class)
data class Note(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    var noteId: Long? = null,

    @Column(name = "title", length = 100, nullable = false)
    var title: String? = null,

    @Column(name = "description", length = 2000)
    var description: String? = null,

//    @CreatedBy // TODO: replace this when accounts enabled
//    @JsonIgnore
    @Column(name = "owner", length = 50, nullable = false)
    var owner: String? = null) : Identifiable<Long> {

    override fun getId(): Long? = noteId
}

@RepositoryRestResource
interface NoteRepository : CrudRepository<Note, Long>
