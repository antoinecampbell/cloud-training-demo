package com.antoinecampbell.cloud.note

import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.data.rest.core.annotation.RepositoryRestResource

@RepositoryRestResource
interface NoteRepository : CrudRepository<Note, Long> {

    @Query("SELECT n FROM Note n WHERE n.owner = ?#{principal}")
    override fun findAll(): List<Note>
}
