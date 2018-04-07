package ml.seniorsigan.imagesaverbot.vk

import com.fasterxml.jackson.databind.ObjectMapper
import ml.seniorsigan.imagesaverbot.vk.handlers.AuthHandler
import ml.seniorsigan.imagesaverbot.vk.handlers.NewMessageHandler
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class VkRouter(
        private val mapper: ObjectMapper,
        authHandler: AuthHandler,
        newMessageHandler: NewMessageHandler
) {
    private val logger = LoggerFactory.getLogger(javaClass)
    private val handlers: Map<String, IVkHandler> = mapOf(
            "confirmation" to authHandler,
            "message_new" to newMessageHandler
    )

    fun delegate(json: String) {
        val type = mapper.readTree(json)["type"].textValue()
        val handler = handlers[type]

        if (handler == null) {
            logger.info("Unknown message type: '$type' for message $json.")
            return
        }

        handler.handle(json)
    }
}