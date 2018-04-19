package ml.seniorsigan.imagesaverbot.anekdot

import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.runBlocking
import ml.seniorsigan.imagesaverbot.AnekdotSources
import org.slf4j.LoggerFactory
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import java.util.concurrent.locks.ReadWriteLock
import java.util.concurrent.locks.ReentrantReadWriteLock
import kotlin.concurrent.withLock

@Service
class AnekdotRepository(
        private val loader: AnekdotLoader,
        private val sources: AnekdotSources
) {
    private var index = 0
    internal var anekdots: List<AnekdotModel> = emptyList()
    private val lock: ReadWriteLock = ReentrantReadWriteLock()
    private val logger = LoggerFactory.getLogger(javaClass)

    fun loadAll() = async {
        anekdots = sources.anekdots.map {
            logger.info("Loading anekdots for $it")
            loader.loadAsync(it)
        }.flatMap { it.await() }
        logger.info("Anekdots loaded")
        index = 0
    }

    @Scheduled(cron = "0 0 6 * * *") // every day at 6AM
    fun load() {
        runBlocking {
            lock.writeLock().withLock {
                loadAll()
            }
        }
    }

    fun next(): AnekdotModel? {
        try {
            if (anekdots.isEmpty()) load()  // TODO: may be kind of post init?
            lock.writeLock().withLock {
                if (index >= anekdots.size) index = 0
                val model = anekdots.getOrNull(index)
                index++
                return model
            }
        } catch (e: Exception) {
            logger.error("Can't get next anekdot: $e", e)
            return null
        }
    }
}