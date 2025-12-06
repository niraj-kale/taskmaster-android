package com.nktest.taskmaster.data.models

import com.nktest.taskmaster.domain.entities.Task

fun TaskDto.toDomain(): Task {
    return Task(
        id = id,
        title = title,
        description = description,
        createdAt = createdAt,
        userId = userId
    )
}

fun Task.toDto(): TaskDto {
    return TaskDto(
        id = id,
        title = title,
        description = description,
        createdAt = createdAt,
        userId = userId
    )
}

