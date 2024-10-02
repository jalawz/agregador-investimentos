package tech.buildrun.agregadorinvestimentos.client.dto

data class BrapiResponseDTO(
    val results: List<StockDTO>
)

data class StockDTO(
    val regularMarketPrice: Double
)