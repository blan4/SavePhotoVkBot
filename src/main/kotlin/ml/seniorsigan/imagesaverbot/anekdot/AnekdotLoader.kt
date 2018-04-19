package ml.seniorsigan.imagesaverbot.anekdot

import kotlinx.coroutines.experimental.async
import org.springframework.stereotype.Service
import java.net.URL
import javax.xml.bind.JAXBContext
import javax.xml.bind.annotation.XmlAccessType
import javax.xml.bind.annotation.XmlAccessorType
import javax.xml.bind.annotation.XmlElement
import javax.xml.bind.annotation.XmlRootElement

@Service
class AnekdotLoader {
    fun load(url: String): List<AnekdotModel> {
        val rss = readXml<RSS>(URL(url))
        return rss.channel.items.map {
            normalize(AnekdotModel(
                    title = it.title,
                    description = it.description,
                    guid = it.guid,
                    pubDate = it.pubDate,
                    link = it.link
            ))
        }
    }

    fun loadAsync(url: String) = async {
        load(url)
    }

    private fun normalize(anekdotModel: AnekdotModel): AnekdotModel {
        return anekdotModel.copy(
                description = anekdotModel.description
                        .replace("<br>", "  \\n")
                        .replace("- ", "- ")
        )
    }

    @XmlRootElement(name = "rss")
    @XmlAccessorType(XmlAccessType.FIELD)
    private open class RSS {
        @XmlElement(name = "channel")
        var channel: Channel = Channel()
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    private open class Channel {
        @XmlElement(name = "item")
        var items: MutableList<Item> = arrayListOf()
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    private open class Item {
        @XmlElement(name = "title")
        var title: String = ""
        @XmlElement(name = "link")
        var link: String = ""
        @XmlElement(name = "pubDate")
        var pubDate: String = ""
        @XmlElement(name = "description")
        var description: String = ""
        @XmlElement(name = "guid")
        var guid: String = ""
    }
}

private inline fun <reified T> readXml(url: URL): T {
    val jc = JAXBContext.newInstance(T::class.java)
    val unmarshaller = jc.createUnmarshaller()
    val data = unmarshaller.unmarshal(url) ?: error("Marshalling failed. Get null object")
    return data as T
}