package tech.buildrun.agregadorinvestimentos.repository

import org.springframework.data.jpa.repository.JpaRepository
import tech.buildrun.agregadorinvestimentos.entity.BillingAddress
import java.util.UUID

interface BillingAddressRepository : JpaRepository<BillingAddress, UUID>