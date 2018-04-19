package ml.seniorsigan.imagesaverbot.controllers

import ml.seniorsigan.imagesaverbot.anekdot.AnekdotRepository
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class HomeController(
        private val repository: AnekdotRepository
) {
    @GetMapping("/")
    fun hello(): String {
        return "Hello World"
    }

    @GetMapping("/anekdot")
    fun anekdot(): String {
        return repository.next()?.description ?: "UPS"
    }
}