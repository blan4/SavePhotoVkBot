package ml.seniorsigan.imagesaverbot

import ml.seniorsigan.imagesaverbot.models.PhotoModel
import org.springframework.stereotype.Service
import java.util.concurrent.locks.ReadWriteLock
import java.util.concurrent.locks.ReentrantReadWriteLock
import kotlin.concurrent.withLock

@Service
class PhotoRepository {
    private val lock: ReadWriteLock = ReentrantReadWriteLock()
    private val storage: MutableList<PhotoModel> = mutableListOf()

    fun insert(photo: PhotoModel) {
        lock.writeLock().withLock {
            storage.add(photo)
        }
    }

    fun getAll(): List<PhotoModel> {
        lock.readLock().withLock {
            return storage.toList()
        }
    }
}