package ml.seniorsigan.imagesaverbot

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class ChatController {
    @GetMapping("/")
    fun hello(): String {
        return "Hello World"
    }
}