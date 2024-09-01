package tech.buildrun.agregadorinvestimentos.entity

import jakarta.persistence.*
import jakarta.persistence.Entity
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import tech.buildrun.agregadorinvestimentos.controller.dto.UserResponse
import java.time.Instant
import java.util.UUID

@Entity
@Table(name = "tb_users")
data class User(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val userId: UUID,
    @Column(name = "username")
    val username: String,
    @Column(name = "email")
    val email: String,
    @Column(name = "password")
    val password: String,
    @CreationTimestamp
    val creationTimestamp: Instant,
    @UpdateTimestamp
    val updateTimestamp: Instant,
    @OneToMany(mappedBy = "user")
    val accounts: List<Account>
) {
    fun toResponse() = UserResponse(
        userId = userId.toString(),
        username,
        email,
        creationTimestamp,
        updateTimestamp
    )
}
