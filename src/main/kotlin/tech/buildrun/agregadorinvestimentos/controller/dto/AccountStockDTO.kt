package tech.buildrun.agregadorinvestimentos.controller.dto

data class AccountStockDTO(
    val stockId: String,
    val quantity: Int
)

data class AccountStockResponse(
    val stockId: String,
    val quantity: Int,
    val total: Double? = 0.0
)