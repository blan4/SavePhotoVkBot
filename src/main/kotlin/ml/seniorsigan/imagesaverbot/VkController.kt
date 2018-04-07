package ml.seniorsigan.imagesaverbot

import ml.seniorsigan.imagesaverbot.vk.VkRouter
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class VkController(
        private val vkRouter: VkRouter
) {
    private val logger = LoggerFactory.getLogger(javaClass)

    @Value("\${vk.confirmation}")
    private lateinit var code: String

    @PostMapping("/api/vk", consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun confirmation(@RequestBody request: String): ResponseEntity<String> {
        logger.info("Received: $request")
        return try {
            vkRouter.delegate(request)
            ResponseEntity.ok(code)
        } catch (e: Exception) {
            logger.error("Can't handle message $request: ${e.message}", e)
            ResponseEntity.badRequest().body(e.message)
        }
    }
}