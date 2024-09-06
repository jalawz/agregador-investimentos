package tech.buildrun.agregadorinvestimentos.repository

import org.springframework.data.jpa.repository.JpaRepository
import tech.buildrun.agregadorinvestimentos.entity.AccountStock
import tech.buildrun.agregadorinvestimentos.entity.AccountStockId
import java.util.UUID

interface AccountStockRepository : JpaRepository<AccountStock, AccountStockId>