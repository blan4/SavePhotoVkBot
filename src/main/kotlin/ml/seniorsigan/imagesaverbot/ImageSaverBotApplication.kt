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

@SpringBootApplication
class ImageSaverBotApplication : SpringBootServletInitializer()

fun main(args: Array<String>) {
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