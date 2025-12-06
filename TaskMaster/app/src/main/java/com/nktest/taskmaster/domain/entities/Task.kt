package com.nktest.taskmaster.domain.entities

data class Task(
    val id: String,
    val title: String,
    val description: String,
    val createdAt: Long,
    val userId: String
)

