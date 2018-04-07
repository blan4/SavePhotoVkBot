package ml.seniorsigan.imagesaverbot.vk.handlers

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import ml.seniorsigan.imagesaverbot.vk.VKHandler
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

@Service
class AuthHandler(
        mapper: ObjectMapper
) : VKHandler(mapper) {
    @Value("\${vk.groupId}")
    private lateinit var groupId: String
    @Value("\${vk.secret}")
    private lateinit var secret: String
    private val logger = LoggerFactory.getLogger(javaClass)

    override fun handle(model: JsonNode) {
        if (model["group_id"].textValue() == groupId && model["secret"].textValue() == secret) {
            // OK
        } else {
            logger.error("Unauthorized request $model. Expected group_id=$groupId and secret=$secret")
            throw Exception("Unauthorized request")
        }
    }
}