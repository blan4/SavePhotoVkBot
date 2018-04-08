package ml.seniorsigan.imagesaverbot.models

data class PhotoModel(
        val url: String?,
        val userId: Long,
        val lat: Double,
        val lon: Double,
        val height: Long,
        val width: Long,
        val date: Long
)