package tech.buildrun.agregadorinvestimentos.controller.dto

import tech.buildrun.agregadorinvestimentos.entity.Account
import tech.buildrun.agregadorinvestimentos.entity.User
import java.util.UUID

data class CreateAccountDTO(
    val description: String,
    val street: String,
    val number: Int
) {
    fun toDomain(user: User) = Account(
        accountId = UUID.randomUUID(),
        description = description,
        user = user,
        billingAddress = null,
        accountStocks = emptyList()
    )
}

data class AccountResponseDTO(
    val accountId: String,
    val description: String
)
