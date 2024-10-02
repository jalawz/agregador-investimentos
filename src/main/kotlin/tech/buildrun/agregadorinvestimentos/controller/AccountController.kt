package tech.buildrun.agregadorinvestimentos.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import tech.buildrun.agregadorinvestimentos.controller.dto.AccountStockDTO
import tech.buildrun.agregadorinvestimentos.controller.dto.AccountStockResponse
import tech.buildrun.agregadorinvestimentos.controller.dto.CreateAccountDTO
import tech.buildrun.agregadorinvestimentos.service.AccountService

@RestController
@RequestMapping("/v1/accounts")
class AccountController(
    private val accountService: AccountService
) {

    @PostMapping("/{accountId}/stocks")
    fun associateStock(
        @PathVariable("accountId") accountId: String,
        @RequestBody request: AccountStockDTO
    ): ResponseEntity<Unit> {
        accountService.associateStock(accountId, request)
        return ResponseEntity.ok().build()
    }

    @GetMapping("/{accountId}/stocks")
    fun listStocks(
        @PathVariable("accountId") accountId: String
    ): ResponseEntity<List<AccountStockResponse>> {
        val stocksResponse = accountService.listAccountStocks(accountId)
        return ResponseEntity.ok(stocksResponse)
    }
}