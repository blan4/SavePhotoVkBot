package ml.seniorsigan.imagesaverbot.anekdot

import kotlinx.coroutines.experimental.runBlocking
import org.junit.Before
import org.junit.Test
import ssl.LetsencryptSslFix

class AnekdotLoaderTest {
    private val loader = AnekdotLoader()

    @Before
    fun setUp() {
        LetsencryptSslFix.fix()
    }

    @Test
    fun shouldParseXml() {
        val url = "https://www.anekdot.ru/rss/tag/26.xml"
        val anekdots = loader.loadAsync(url)
        runBlocking {
            val arr = anekdots.await()
            println(arr.size)
            println(arr)
        }
    }
}