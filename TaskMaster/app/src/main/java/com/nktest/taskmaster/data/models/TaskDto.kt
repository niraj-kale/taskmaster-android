package com.nktest.taskmaster.data.models

data class TaskDto(
    val id: String = "",
    val title: String = "",
    val description: String = "",
    val createdAt: Long = 0L,
    val userId: String = ""
)

