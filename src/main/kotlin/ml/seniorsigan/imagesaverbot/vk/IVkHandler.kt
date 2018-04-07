package ml.seniorsigan.imagesaverbot.vk

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper

interface IVkHandler {
    fun handle(json: String)
}

abstract class VKHandler(
        private val mapper: ObjectMapper
) : IVkHandler {
    override fun handle(json: String) {
        handle(mapper.readTree(json))
    }

    abstract fun handle(model: JsonNode)
}