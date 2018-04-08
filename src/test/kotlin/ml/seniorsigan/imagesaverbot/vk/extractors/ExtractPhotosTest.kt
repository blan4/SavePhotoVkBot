package ml.seniorsigan.imagesaverbot.vk.extractors

import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class ExtractPhotosTest {
    private val extractor = ExtractPhotos()
    private val mapper = ObjectMapper()
    private val jsonStr = """
        {
          "group_id": 141957789,
          "object": {
            "attachments": [
              {
                "photo": {
                  "access_key": "af21b89a2a93a9234e",
                  "album_id": -3,
                  "date": 1523128757,
                  "height": 960,
                  "id": 456239545,
                  "lat": 54.983589,
                  "long": 73.375623,
                  "owner_id": 38057738,
                  "photo_130": "https://pp.userapi.com/c846017/v846017600/1cc30/V5o8wqvDK2Y.jpg",
                  "photo_604": "https://pp.userapi.com/c846017/v846017600/1cc31/mH1la8Rlmf8.jpg",
                  "photo_75": "https://pp.userapi.com/c846017/v846017600/1cc2f/1gbhhIIjsF4.jpg",
                  "photo_807": "https://pp.userapi.com/c846017/v846017600/1cc32/ZsBo4GuDH8Y.jpg",
                  "text": "",
                  "width": 720
                },
                "type": "photo"
              }
            ],
            "body": "",
            "date": 1523128762,
            "id": 5,
            "out": 0,
            "read_state": 0,
            "title": "",
            "user_id": 38057738
          },
          "secret": "EA30EC436A8D4C15AC04F2D99F381EEA",
          "type": "message_new"
        }
    """.trimIndent()

    @Test
    fun shouldExtractPhoto() {
        val json = mapper.readTree(jsonStr)
        val photos = extractor.extract(json)
        assertTrue(photos.size == 1)
        assertEquals(38057738, photos.first().userId)
        assertEquals("https://pp.userapi.com/c846017/v846017600/1cc32/ZsBo4GuDH8Y.jpg", photos.first().url)
    }
}