package tech.buildrun.agregadorinvestimentos.repository

import org.springframework.data.jpa.repository.JpaRepository
import tech.buildrun.agregadorinvestimentos.entity.Account
import java.util.UUID

interface AccountRepository : JpaRepository<Account, UUID>