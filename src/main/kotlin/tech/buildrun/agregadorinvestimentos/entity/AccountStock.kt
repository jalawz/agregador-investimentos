package tech.buildrun.agregadorinvestimentos.entity

import jakarta.persistence.Column
import jakarta.persistence.Embeddable
import jakarta.persistence.EmbeddedId
import jakarta.persistence.Entity
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.MapsId
import jakarta.persistence.Table
import java.util.UUID

@Entity
@Table(name = "tb_accounts_stocks")
data class AccountStock(
    @EmbeddedId
    val id: AccountStockId,

    val quantity: Int,

    @ManyToOne
    @MapsId("accountId")
    @JoinColumn(name = "account_id")
    val account: Account,

    @ManyToOne
    @MapsId("stockId")
    @JoinColumn(name = "stock_id")
    val stock: Stock,
)

@Embeddable
data class AccountStockId(
    @Column(name = "account_id")
    val accountId: UUID,
    @Column(name = "stock_id")
    val stockId: String
)
