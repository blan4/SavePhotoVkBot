package ml.seniorsigan.imagesaverbot

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.web.support.SpringBootServletInitializer

@SpringBootApplication
class ImageSaverBotApplication : SpringBootServletInitializer()

fun main(args: Array<String>) {
    SpringApplication.run(ImageSaverBotApplication::class.java, *args)
}
