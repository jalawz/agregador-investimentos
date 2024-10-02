package tech.buildrun.agregadorinvestimentos.service

import org.springframework.stereotype.Service
import tech.buildrun.agregadorinvestimentos.controller.dto.CreateAccountDTO
import tech.buildrun.agregadorinvestimentos.controller.dto.UserCreateRequest
import tech.buildrun.agregadorinvestimentos.controller.dto.UserUpdateRequest
import tech.buildrun.agregadorinvestimentos.entity.Account
import tech.buildrun.agregadorinvestimentos.entity.BillingAddress
import tech.buildrun.agregadorinvestimentos.entity.User
import tech.buildrun.agregadorinvestimentos.exception.ResourceNotFoundException
import tech.buildrun.agregadorinvestimentos.repository.AccountRepository
import tech.buildrun.agregadorinvestimentos.repository.BillingAddressRepository
import tech.buildrun.agregadorinvestimentos.repository.UserRepository
import java.time.Instant
import java.util.Optional
import java.util.UUID

@Service
class UserService(
    private val userRepository: UserRepository,
    private val accountRepository: AccountRepository,
    private val billingAddressRepository: BillingAddressRepository
) {

    fun createUser(userDto: UserCreateRequest): User {
        return userRepository.save(userDto.toDomain())
    }

    fun getUserById(userId: String): Optional<User> {
        return userRepository.findById(UUID.fromString(userId))
    }

    fun getAllUsers(): List<User> {
        return userRepository.findAll()
    }

    fun updateUserById(userId: String, updateRequest: UserUpdateRequest): User {
        val uuid = UUID.fromString(userId)
        val user = userRepository.findById(uuid)

        if (user.isPresent) {
            val userEntity = user.get()
            return userRepository
                .save(
                    userEntity.copy(
                        username = updateRequest.username,
                        password = updateRequest.password,
                        updateTimestamp = Instant.now()
                    )
                )
        }

        throw ResourceNotFoundException("User with id: $userId not found")
    }

    fun deleteUserById(userId: String) {
        val uuid = UUID.fromString(userId)
        val userExists = userRepository.existsById(uuid)

        if (userExists) {
            userRepository.deleteById(uuid)
        }

        throw ResourceNotFoundException("User with id: $userId not found")
    }

    fun createAccount(userId: String, request: CreateAccountDTO) {
        val user = findUserById(userId)

        val accountCreated = accountRepository.save(request.toDomain(user))
        val billingAddress = BillingAddress(
            accountCreated.accountId,
            request.street,
            request.number,
            accountCreated
        )

        billingAddressRepository.save(billingAddress)
    }


    fun listAccounts(userId: String): List<Account> {
        val user = findUserById(userId)

        return user.accounts
    }

    private fun findUserById(userId: String): User =
        userRepository.findById(UUID.fromString(userId))
            .orElseThrow { ResourceNotFoundException("User with id: $userId not found") }
}