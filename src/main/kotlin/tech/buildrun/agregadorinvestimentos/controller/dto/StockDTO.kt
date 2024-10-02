package tech.buildrun.agregadorinvestimentos.controller.dto

import tech.buildrun.agregadorinvestimentos.entity.Stock

data class CreateStockDTO(
    val stockId: String,
    val description: String
) {
    fun toDomain() = Stock(
        stockId = stockId,
        description = description
    )
}
