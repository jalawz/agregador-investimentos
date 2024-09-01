package tech.buildrun.agregadorinvestimentos.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToOne
import jakarta.persistence.PrimaryKeyJoinColumn
import jakarta.persistence.Table
import java.util.UUID

@Entity
@Table(name = "tb_accounts")
data class Account(
    @Id
    @Column(name = "account_id")
    @GeneratedValue(strategy = GenerationType.UUID)
    val accountId: UUID,

    @Column(name = "description")
    val description: String,

    @ManyToOne
    @JoinColumn(name = "user_id")
    val user: User,

    @OneToOne(mappedBy = "account")
    @PrimaryKeyJoinColumn
    val billingAddress: BillingAddress
)
