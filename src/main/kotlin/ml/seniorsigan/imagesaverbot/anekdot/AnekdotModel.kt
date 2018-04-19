package ml.seniorsigan.imagesaverbot.anekdot

data class AnekdotModel(
        val title: String,
        val description: String,
        val guid: String,
        val pubDate: String,
        val link: String
) {
    val body = """
        $title
        $description
    """.trimIndent()
}