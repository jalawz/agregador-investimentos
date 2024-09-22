package tech.buildrun.agregadorinvestimentos.factory

import tech.buildrun.agregadorinvestimentos.entity.User
import java.time.Instant
import java.util.UUID

object UserFactory {

    fun userMock(
        userId: UUID = UUID.randomUUID(),
        username: String = "jalawz",
        email: String = "jalawz@gmail.com",
        password: String = "password"
    ) = User(
        userId,
        username,
        email,
        password,
        Instant.now(),
        Instant.now(),
        emptyList()
    )
}