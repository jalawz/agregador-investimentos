package tech.buildrun.agregadorinvestimentos.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import tech.buildrun.agregadorinvestimentos.controller.dto.CreateStockDTO
import tech.buildrun.agregadorinvestimentos.entity.User
import tech.buildrun.agregadorinvestimentos.service.StockService
import java.net.URI

@RestController
@RequestMapping("/v1/stocks")
class StockController(
    private val stockService: StockService
) {

    @PostMapping
    fun createStock(@RequestBody createStockDTO: CreateStockDTO): ResponseEntity<User> {
        val stock = stockService.createStock(createStockDTO)
        return ResponseEntity
            .created(URI.create("/v1/stocks/${stock.stockId}")).build()
    }
}