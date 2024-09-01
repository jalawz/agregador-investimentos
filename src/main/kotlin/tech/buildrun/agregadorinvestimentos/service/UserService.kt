package tech.buildrun.agregadorinvestimentos.service

import org.springframework.stereotype.Service
import tech.buildrun.agregadorinvestimentos.controller.dto.UserCreateRequest
import tech.buildrun.agregadorinvestimentos.controller.dto.UserUpdateRequest
import tech.buildrun.agregadorinvestimentos.entity.User
import tech.buildrun.agregadorinvestimentos.exception.ResourceNotFoundException
import tech.buildrun.agregadorinvestimentos.repository.UserRepository
import java.time.Instant
import java.util.Optional
import java.util.UUID

@Service
class UserService(
    private val userRepository: UserRepository
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
}