package tech.buildrun.agregadorinvestimentos.service

import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException
import tech.buildrun.agregadorinvestimentos.client.BrapiClient
import tech.buildrun.agregadorinvestimentos.controller.dto.AccountStockDTO
import tech.buildrun.agregadorinvestimentos.controller.dto.AccountStockResponse
import tech.buildrun.agregadorinvestimentos.entity.AccountStock
import tech.buildrun.agregadorinvestimentos.entity.AccountStockId
import tech.buildrun.agregadorinvestimentos.repository.AccountRepository
import tech.buildrun.agregadorinvestimentos.repository.AccountStockRepository
import tech.buildrun.agregadorinvestimentos.repository.StockRepository
import java.util.*

@Service
class AccountService(
    private val accountRepository: AccountRepository,
    private val stockRepository: StockRepository,
    private val accountStockRepository: AccountStockRepository,
    private val brapiClient: BrapiClient,
    @Value("#{environment.TOKEN}")
    private val token: String
) {
    fun associateStock(accountId: String, request: AccountStockDTO) {
        val account = accountRepository.findById(UUID.fromString(accountId)).orElseThrow {
            ResponseStatusException(HttpStatus.NOT_FOUND)
        }

        val stock = stockRepository.findById(request.stockId).orElseThrow {
            ResponseStatusException(HttpStatus.NOT_FOUND)
        }

        val id = AccountStockId(account.accountId, stock.stockId)
        val accountStock = AccountStock(
            id = id,
            quantity = request.quantity,
            account = account,
            stock = stock
        )

        accountStockRepository.save(accountStock)

    }

    fun listAccountStocks(accountId: String): List<AccountStockResponse> {
        val account = accountRepository.findById(UUID.fromString(accountId)).orElseThrow {
            ResponseStatusException(HttpStatus.NOT_FOUND)
        }

        return account.accountStocks.map {
            it.toResponse(
                total = getTotal(it.quantity, it.stock.stockId)
            )
        }
    }

    private fun getTotal(quantity: Int, stockId: String): Double {
        val response = brapiClient.getQuote(token, stockId)
        val price = response.results.first().regularMarketPrice

        return quantity * price
    }
}