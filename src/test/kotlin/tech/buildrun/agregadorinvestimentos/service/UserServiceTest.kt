package tech.buildrun.agregadorinvestimentos.service

import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.boot.test.context.SpringBootTest
import tech.buildrun.agregadorinvestimentos.controller.dto.UserCreateRequest
import tech.buildrun.agregadorinvestimentos.controller.dto.UserUpdateRequest
import tech.buildrun.agregadorinvestimentos.entity.User
import tech.buildrun.agregadorinvestimentos.exception.ResourceNotFoundException
import tech.buildrun.agregadorinvestimentos.factory.UserFactory
import tech.buildrun.agregadorinvestimentos.repository.UserRepository
import java.time.Instant
import java.util.Optional
import java.util.UUID
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

@SpringBootTest
class UserServiceTest {

    private val userRepository = mockk<UserRepository>()
    private val userService = UserService(userRepository)

    @Nested
    inner class CreateUser {

        @Test
        @DisplayName("Should create a user with success.")
        fun shouldCreateAUser() {
            // Arrange
            val slot = slot<User>()
            every { userRepository.save(capture(slot)) } returns User(
                UUID.randomUUID(),
                "jalawz",
                "jalawz@gmaill.com",
                "password",
                Instant.now(),
                Instant.now()
            )

            val input = UserCreateRequest(
                username = "jalawz",
                email = "jalawz@gmail.com",
                password = "1234"
            )
            // Act
            val output = userService.createUser(input);

            // Assert
            assertNotNull(output)
            assertEquals(input.username, slot.captured.username)
            assertNotNull(slot.captured.creationTimestamp)
            assertNotNull(slot.captured.userId)
        }

        @Test
        fun shouldThrowExceptionWhenErrorOccurs() {
            // Arrange
            every { userRepository.save(any()) } throws RuntimeException("Exception")


            val input = UserCreateRequest(
                username = "jalawz",
                email = "jalawz@gmail.com",
                password = "1234"
            )

            // Assert
            assertThrows<RuntimeException> {
                userService.createUser(input)
            }
        }
    }

    @Nested
    inner class GetUserById {

        @Test
        @DisplayName("Should get user by id with success when optional is present")
        fun shouldGetUserByIdWithSuccess() {
            val slot = slot<UUID>()
            val user = User(
                UUID.randomUUID(),
                "jalawz",
                "jalawz@gmaill.com",
                "password",
                Instant.now(),
                Instant.now()
            )
            every { userRepository.findById(capture(slot)) } returns Optional.of(user)

            val output = userService.getUserById(user.userId.toString())

            assertTrue(output.isPresent)
            assertEquals(user.userId, slot.captured)
        }

        @Test
        @DisplayName("Should get user by id with success when optional is empty")
        fun shouldGetUserByIdWithSuccessWhenOptionalIsEmpty() {
            val slot = slot<UUID>()
            val userId = UUID.randomUUID()
            every { userRepository.findById(capture(slot)) } returns Optional.empty()

            val output = userService.getUserById(userId.toString())

            assertTrue(output.isEmpty)
            assertEquals(userId, slot.captured)
        }
    }

    @Nested
    inner class GetAllUsers {

        @Test
        @DisplayName("Should fetch all users with success.")
        fun shouldGetAllUsers() {
            // Arrange
            val user = User(
                UUID.randomUUID(),
                "jalawz",
                "jalawz@gmaill.com",
                "password",
                Instant.now(),
                Instant.now()
            )
            every { userRepository.findAll() } returns listOf(user)
            // Act
            val output = userService.getAllUsers();

            // Assert
            assertNotNull(output)
            assertEquals(output.size, 1)
        }

        @Test
        @DisplayName("Should fetch empty list of users with success.")
        fun shouldGetEmptyUsersList() {
            // Arrange

            every { userRepository.findAll() } returns emptyList()
            // Act
            val output = userService.getAllUsers();

            // Assert
            assertNotNull(output)
            assertEquals(output.size, 0)
        }
    }

    @Nested
    inner class UpdateUserById() {
        @Test
        @DisplayName("Should update a user with success.")
        fun shouldUpdateAUser() {
            // Arrange
            val userIdSlot = slot<UUID>()
            val userSlot = slot<User>()
            val userMock = UserFactory.userMock()

            every { userRepository.findById(capture(userIdSlot)) } returns Optional.of(userMock)

            every { userRepository.save(capture(userSlot)) } returns userMock

            val input = UserUpdateRequest(
                username = "jalawzinhu",
                password = "1234"
            )
            // Act
            val output = userService.updateUserById(USER_ID.toString(), input);

            // Assert
            assertNotNull(output)
            assertEquals(input.username, userSlot.captured.username)
            assertEquals(input.password, userSlot.captured.password)
            assertNotEquals(userMock.updateTimestamp, userSlot.captured.updateTimestamp)

        }

        @Test
        @DisplayName("Should throw Resource Not Found when user does not exist.")
        fun shouldThrowExceptionWhenUserNotFound() {
            // Arrange
            val userIdSlot = slot<UUID>()

            every { userRepository.findById(capture(userIdSlot)) } returns Optional.empty()

            val input = UserUpdateRequest(
                username = "jalawzinhu",
                password = "1234"
            )

            assertThrows<ResourceNotFoundException> {
                userService.updateUserById(USER_NOT_FOUND_ID.toString(), input);
                assertEquals(userIdSlot.captured, USER_NOT_FOUND_ID)
            }

        }
    }

    companion object {
        private val USER_ID = UUID.randomUUID()
        private val USER_NOT_FOUND_ID = UUID.randomUUID()
    }
}