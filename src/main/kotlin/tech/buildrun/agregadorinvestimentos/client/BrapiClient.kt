package tech.buildrun.agregadorinvestimentos.client

import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import tech.buildrun.agregadorinvestimentos.client.dto.BrapiResponseDTO

@FeignClient(
    name = "BrapiClient",
    url = "\${brapi.url}"
)
interface BrapiClient {

    @GetMapping("/api/quote/{stockId}")
    fun getQuote(
        @RequestParam("token") token: String,
        @RequestParam("stockId") stockId: String
    ): BrapiResponseDTO
}