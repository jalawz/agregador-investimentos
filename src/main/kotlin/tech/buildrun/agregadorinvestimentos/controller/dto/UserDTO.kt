package tech.buildrun.agregadorinvestimentos.controller.dto

import tech.buildrun.agregadorinvestimentos.entity.User
import java.time.Instant
import java.util.*


data class UserResponse(
    val userId: String,
    val username: String,
    val email: String,
    val createdAt: Instant,
    val updatedAt: Instant
)

data class UserCreateRequest(
    val username: String,
    val email: String,
    val password: String,
) {
    fun toDomain() = User(
        userId = UUID.randomUUID(),
        username,
        email,
        password,
        creationTimestamp = Instant.now(),
        updateTimestamp = Instant.now()
    )
}

data class UserUpdateRequest(
    val username: String,
    val password: String
)