package ml.seniorsigan.imagesaverbot.anekdot

import kotlinx.coroutines.experimental.runBlocking
import ml.seniorsigan.imagesaverbot.ImageSaverBotApplication
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner
import ssl.LetsencryptSslFix

@RunWith(SpringJUnit4ClassRunner::class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.NONE,
        classes = [ImageSaverBotApplication::class]
)
class AnekdotRepositoryTest {
    @Autowired
    private lateinit var repository: AnekdotRepository

    @Before
    fun setUp() {
        LetsencryptSslFix.fix()
    }

    @Test
    fun shouldLoadAll() {
        runBlocking {
            repository.loadAll().await()
            println(repository.anekdots.size)
            println(repository.anekdots)
        }
    }
}