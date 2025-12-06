package com.nktest.taskmaster.data.datasources

import com.google.firebase.firestore.FirebaseFirestore
import com.nktest.taskmaster.data.models.TaskDto
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FirestoreTaskDataSource @Inject constructor(
    private val firestore: FirebaseFirestore
) : TaskDataSource {

    private companion object {
        const val COLLECTION_TASKS = "tasks"
    }

    override suspend fun create(task: TaskDto): TaskDto {
        val docRef = if (task.id.isBlank()) {
            firestore.collection(COLLECTION_TASKS).document()
        } else {
            firestore.collection(COLLECTION_TASKS).document(task.id)
        }
        docRef.set(task.copy(id = docRef.id)).await()
        return task.copy(id = docRef.id)
    }

    override suspend fun getById(id: String): TaskDto? {
        return try {
            val document = firestore.collection(COLLECTION_TASKS).document(id).get().await()
            document.toObject(TaskDto::class.java)?.copy(id = document.id)
        } catch (e: Exception) {
            null
        }
    }

    override suspend fun getAll(userId: String): List<TaskDto> {
        return try {
            val snapshot = firestore.collection(COLLECTION_TASKS)
                .whereEqualTo("userId", userId)
                .get()
                .await()
            snapshot.documents.mapNotNull { doc ->
                doc.toObject(TaskDto::class.java)?.copy(id = doc.id)
            }
        } catch (e: Exception) {
            emptyList()
        }
    }

    override suspend fun update(task: TaskDto): TaskDto {
        firestore.collection(COLLECTION_TASKS).document(task.id).set(task).await()
        return task
    }

    override suspend fun delete(id: String) {
        firestore.collection(COLLECTION_TASKS).document(id).delete().await()
    }
}

