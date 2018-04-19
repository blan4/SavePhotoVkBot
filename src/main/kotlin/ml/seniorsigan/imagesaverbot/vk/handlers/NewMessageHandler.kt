package ml.seniorsigan.imagesaverbot.vk.handlers

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import ml.seniorsigan.imagesaverbot.IPhotoRepository
import ml.seniorsigan.imagesaverbot.vk.MessageSender
import ml.seniorsigan.imagesaverbot.vk.VKHandler
import ml.seniorsigan.imagesaverbot.vk.extractors.ExtractPhotos
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class NewMessageHandler(
        private val extractor: ExtractPhotos,
        private val photoRepository: IPhotoRepository,
        private val sender: MessageSender,
        mapper: ObjectMapper
) : VKHandler(mapper) {
    private val logger = LoggerFactory.getLogger(javaClass)

    override fun handle(model: JsonNode): String {
        val photos = extractor.extract(model)
        logger.info("Extracted photos: $photos")
        photos.forEach {
            photoRepository.insert(it)
        }
        val userId = photos.firstOrNull()?.userId?.toInt()
        if (userId != null) sender.send(userId)
        return "ok"
    }
}