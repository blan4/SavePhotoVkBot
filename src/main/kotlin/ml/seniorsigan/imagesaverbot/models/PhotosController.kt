package ml.seniorsigan.imagesaverbot.models

import ml.seniorsigan.imagesaverbot.PhotoRepository
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class PhotosController(
        private val photoRepository: PhotoRepository
) {
    private val logger = LoggerFactory.getLogger(javaClass)

    @GetMapping("/photos")
    fun getAll(): List<PhotoModel> {
        return photoRepository.getAll()
    }
}