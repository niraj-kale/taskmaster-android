package com.nktest.taskmaster.data.local

import com.nktest.taskmaster.domain.entities.Task

fun TaskEntity.toDomain(): Task {
    return Task(
        id = id,
        title = title,
        description = description,
        createdAt = createdAt,
        userId = userId
    )
}

fun Task.toEntity(): TaskEntity {
    return TaskEntity(
        id = id,
        title = title,
        description = description,
        createdAt = createdAt,
        userId = userId
    )
}

