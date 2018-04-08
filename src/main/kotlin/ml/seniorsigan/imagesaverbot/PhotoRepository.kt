package ml.seniorsigan.imagesaverbot

import com.google.cloud.datastore.*
import ml.seniorsigan.imagesaverbot.models.PhotoModel
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.util.concurrent.locks.ReadWriteLock
import java.util.concurrent.locks.ReentrantReadWriteLock
import kotlin.concurrent.withLock

interface IPhotoRepository {
    fun insert(photo: PhotoModel)
    fun getAll(): List<PhotoModel>
}

@Service
class PhotoDatastoreRepository(
        private val datastore: Datastore
) : IPhotoRepository {
    private val logger = LoggerFactory.getLogger(javaClass)
    private val kind = "Photos"
    private val keyFactory: KeyFactory = datastore.newKeyFactory().setKind(kind)

    override fun insert(photo: PhotoModel) {
        val entity = photo.toEntity()
        val ent = datastore.add(entity)
        logger.info("Saved $photo with key=${ent.key.name}")
    }

    override fun getAll(): List<PhotoModel> {
        val query = Query.newEntityQueryBuilder()
                .setKind(kind)
                .setOrderBy(StructuredQuery.OrderBy.asc("date"))
                .build()
        val results = datastore.run(query)
        return results.asSequence().map { it.toPhotoModel() }.toList()
    }

    private fun PhotoModel.toEntity() =
            Entity.newBuilder(keyFactory.newKey())
                    .set("url", url)
                    .set("userId", userId)
                    .set("coordinates", LatLng.of(lat, lon))
                    .set("height", height)
                    .set("width", width)
                    .set("date", date)
                    .build()

    private fun Entity.toPhotoModel() =
            PhotoModel(
                    url = getString("url"),
                    userId = getLong("userId"),
                    lat = getLatLng("coordinates").latitude,
                    lon = getLatLng("coordinates").longitude,
                    height = getLong("height"),
                    width = getLong("width"),
                    date = getLong("date")
            )
}

class PhotoInMemoryRepository : IPhotoRepository {
    private val lock: ReadWriteLock = ReentrantReadWriteLock()
    private val storage: MutableList<PhotoModel> = mutableListOf()

    override fun insert(photo: PhotoModel) {
        lock.writeLock().withLock {
            storage.add(photo)
        }
    }

    override fun getAll(): List<PhotoModel> {
        lock.readLock().withLock {
            return storage.toList()
        }
    }
}