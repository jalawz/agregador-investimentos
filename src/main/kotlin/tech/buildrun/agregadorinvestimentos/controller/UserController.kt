package tech.buildrun.agregadorinvestimentos.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import tech.buildrun.agregadorinvestimentos.controller.dto.*
import tech.buildrun.agregadorinvestimentos.entity.User
import tech.buildrun.agregadorinvestimentos.service.UserService
import java.net.URI

@RestController
@RequestMapping("/v1/users")
class UserController(
    private val userService: UserService
) {

    @PostMapping
    fun createUser(@RequestBody userCreateRequest: UserCreateRequest): ResponseEntity<User> {
        val userCreated = userService.createUser(userCreateRequest)
        return ResponseEntity
            .created(URI.create("/v1/users/${userCreated.userId}")).build()
    }

    @GetMapping
    fun getUsers(): ResponseEntity<List<UserResponse>> {
        val users = userService.getAllUsers().map { it.toResponse() }

        return ResponseEntity.ok(users)
    }

    @GetMapping("/{userId}")
    fun findUserById(@PathVariable userId: String): ResponseEntity<UserResponse> {
        val user = userService.getUserById(userId)

        if (user.isPresent) {
            return ResponseEntity.ok(user.get().toResponse())
        }

        return ResponseEntity.notFound().build()
    }

    @PutMapping("/{userId}")
    fun updateUserById(@PathVariable userId: String,
                       @RequestBody userUpdateRequest: UserUpdateRequest): ResponseEntity.HeadersBuilder<*> {
        userService.updateUserById(userId, userUpdateRequest)
        return ResponseEntity.noContent()
    }

    @DeleteMapping("/{userId}")
    fun deleteUserById(@PathVariable userId: String): ResponseEntity.HeadersBuilder<*> {
        userService.deleteUserById(userId)
        return ResponseEntity.noContent()
    }

    @PostMapping("/{userId}/accounts")
    fun createUserAccount(@PathVariable userId: String, @RequestBody request: CreateAccountDTO): ResponseEntity<Unit> {
        userService.createAccount(userId, request)
        return ResponseEntity.ok().build()
    }

    @GetMapping("/{userId}/accounts")
    fun getUserAccount(@PathVariable userId: String): ResponseEntity<List<AccountResponseDTO>> {
        val accounts = userService.listAccounts(userId)
            .map {
                AccountResponseDTO(it.accountId.toString(), it.description)
            }
        return ResponseEntity.ok(accounts)
    }
}