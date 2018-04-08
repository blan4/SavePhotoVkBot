package ml.seniorsigan.imagesaverbot.vk

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper

interface IVkHandler {
    fun handle(json: String): String
}

abstract class VKHandler(
        private val mapper: ObjectMapper
) : IVkHandler {
    override fun handle(json: String): String {
        return handle(mapper.readTree(json))
    }

    abstract fun handle(model: JsonNode): String
}