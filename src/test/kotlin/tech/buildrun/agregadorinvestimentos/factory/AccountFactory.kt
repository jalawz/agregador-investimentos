package tech.buildrun.agregadorinvestimentos.factory

import tech.buildrun.agregadorinvestimentos.controller.dto.CreateAccountDTO
import tech.buildrun.agregadorinvestimentos.entity.Account
import java.util.*

object AccountFactory {

    fun createAccountMock() = CreateAccountDTO(
        description = "Conta Loka",
        street = "Rua da Amargura",
        number = 352
    )

    fun accountMock() = Account(
        accountId = UUID.randomUUID(),
        description = "Conta Loka",
        user = UserFactory.userMock(),
        billingAddress = null,
        accountStocks = emptyList()
    )
}