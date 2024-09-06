package tech.buildrun.agregadorinvestimentos.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "tb_stocks")
data class Stock(
    @Id
    @Column(name = "stock_id")
    val stockId: String,
    @Column(name = "description")
    val description: String
)
