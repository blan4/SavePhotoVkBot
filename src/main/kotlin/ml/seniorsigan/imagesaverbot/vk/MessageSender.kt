package ml.seniorsigan.imagesaverbot.vk

import com.vk.api.sdk.client.VkApiClient
import com.vk.api.sdk.client.actors.GroupActor
import ml.seniorsigan.imagesaverbot.anekdot.AnekdotRepository
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.util.*

@Service
class MessageSender(
        private val vk: VkApiClient,
        private val actor: GroupActor,
        private val anekdotRepository: AnekdotRepository
) {
    private val logger = LoggerFactory.getLogger(javaClass)
    private val random = Random()

    fun send(userId: Int) {
        val message = anekdotRepository.next()?.body ?: "Спасибо ^_^"
        try {
            vk.messages().send(actor)
                    .message(message)
                    .userId(userId)
                    .randomId(random.nextInt())
                    .execute()
        } catch (e: Exception) {
            logger.error("Can't send message $message to user $userId: ${e.message}", e)
        }
    }
}