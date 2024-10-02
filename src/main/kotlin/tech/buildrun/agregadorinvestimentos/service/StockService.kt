package tech.buildrun.agregadorinvestimentos.service

import org.springframework.stereotype.Service
import tech.buildrun.agregadorinvestimentos.controller.dto.CreateStockDTO
import tech.buildrun.agregadorinvestimentos.entity.Stock
import tech.buildrun.agregadorinvestimentos.repository.StockRepository

@Service
class StockService(
    private val stockRepository: StockRepository
) {
    fun createStock(createStockDTO: CreateStockDTO): Stock {
        return stockRepository.save(createStockDTO.toDomain())
    }
}