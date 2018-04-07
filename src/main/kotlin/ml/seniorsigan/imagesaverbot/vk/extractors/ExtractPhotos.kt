package ml.seniorsigan.imagesaverbot.vk.extractors

import com.fasterxml.jackson.databind.JsonNode
import ml.seniorsigan.imagesaverbot.models.PhotoModel
import org.springframework.stereotype.Service

@Service
class ExtractPhotos {
    fun extract(json: JsonNode): List<PhotoModel> {
        return json["object"]["attachments"].asIterable().filter {
            it["type"].textValue() == "photo"
        }.map {
            val photo = it["photo"]
            val url = photo["photo_1280"].textValue() ?: photo["photo_807"].textValue()
            ?: photo["photo_604"].textValue() ?: photo["photo_130"].textValue() ?: photo["photo_75"].textValue()
            PhotoModel(
                    url = url,
                    userId = photo["owner_id"].longValue()
            )
        }
    }
}