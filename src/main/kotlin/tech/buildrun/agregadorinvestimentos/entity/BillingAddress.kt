package tech.buildrun.agregadorinvestimentos.entity

import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.MapsId
import jakarta.persistence.OneToOne
import jakarta.persistence.Table
import java.util.UUID

@Entity
@Table(name = "tb_billing_address")
data class BillingAddress(
    @Id
    @Column(name = "account_id")
    val id: UUID,

    @Column(name = "street")
    val street: String,

    @Column(name = "number")
    val number: Int,

    @OneToOne(cascade = [CascadeType.ALL])
    @MapsId
    @JoinColumn(name = "account_id")
    val account: Account
)
