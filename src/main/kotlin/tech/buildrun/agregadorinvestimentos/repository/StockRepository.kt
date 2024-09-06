package tech.buildrun.agregadorinvestimentos.repository

import org.springframework.data.jpa.repository.JpaRepository
import tech.buildrun.agregadorinvestimentos.entity.Stock

interface StockRepository : JpaRepository<Stock, String>