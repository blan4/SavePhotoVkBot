package ml.seniorsigan.imagesaverbot

import com.google.cloud.datastore.Datastore
import com.google.cloud.datastore.DatastoreOptions
import com.vk.api.sdk.client.VkApiClient
import com.vk.api.sdk.client.actors.GroupActor
import com.vk.api.sdk.httpclient.HttpTransportClient
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.web.support.SpringBootServletInitializer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.annotation.EnableScheduling
import ssl.LetsencryptSslFix

@SpringBootApplication
@EnableScheduling
class ImageSaverBotApplication : SpringBootServletInitializer()

fun main(args: Array<String>) {
    LetsencryptSslFix.fix()
    SpringApplication.run(ImageSaverBotApplication::class.java, *args)
}

@Configuration
class GCDatastoreConfiguration {
    @Bean
    fun datastore(): Datastore {
        return DatastoreOptions.getDefaultInstance().service
    }
}

@Configuration
class VkAPiConfiguration {
    @Value("\${vk.groupId}")
    private lateinit var groupId: String
    @Value("\${vk.token}")
    private lateinit var token: String

    @Bean
    fun vkApiClient(): VkApiClient {
        val transport = HttpTransportClient()
        return VkApiClient(transport)
    }

    @Bean
    fun groupActor(): GroupActor {
        return GroupActor(groupId.toInt(), token)
    }
}

@Configuration
class AnekdotSources {
    val anekdots = listOf(
            "https://www.anekdot.ru/rss/tag/26.xml",
            "https://www.anekdot.ru/rss/tag/21.xml",
            "https://www.anekdot.ru/rss/tag/33.xml",
            "https://www.anekdot.ru/rss/tag/37.xml",
            "https://www.anekdot.ru/rss/tag/40.xml",
            "https://www.anekdot.ru/rss/export_bestday.xml",
            "https://www.anekdot.ru/rss/export_j.xml"
    )
}