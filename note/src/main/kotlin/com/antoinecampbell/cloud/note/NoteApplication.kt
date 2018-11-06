package com.antoinecampbell.cloud.note

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.client.discovery.EnableDiscoveryClient

@SpringBootApplication
@EnableDiscoveryClient
class NoteApplication

fun main(args: Array<String>) {
    runApplication<NoteApplication>(*args)
}
