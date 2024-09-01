package tech.buildrun.agregadorinvestimentos.factory

import tech.buildrun.agregadorinvestimentos.entity.User
import java.time.Instant
import java.util.UUID

object UserFactory {

    fun userMock() = User(
        UUID.randomUUID(),
        "jalawz",
        "jalawz@gmaill.com",
        "password",
        Instant.now(),
        Instant.now()
    )
}