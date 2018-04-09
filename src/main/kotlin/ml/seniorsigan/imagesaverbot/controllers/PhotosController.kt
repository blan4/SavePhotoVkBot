package ml.seniorsigan.imagesaverbot.controllers

import ml.seniorsigan.imagesaverbot.IPhotoRepository
import ml.seniorsigan.imagesaverbot.models.PhotoModel
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class PhotosController(
        private val photoRepository: IPhotoRepository
) {
    private val logger = LoggerFactory.getLogger(javaClass)

    @GetMapping("/photos")
    fun getAll(): List<PhotoModel> {
        return photoRepository.getAll()
    }

    @GetMapping("/count")
    fun getCount(): Int {
        return photoRepository.getAll().groupBy { it.url }.size
    }
}