data class Restaurants(
    val name: String?,
    val pageTitle: String?,
    val sections: List<Sections>
)

data class Sections(
    val items: List<Items>?,
    val contentType: String?,
    val name: String?,
)

data class Items(
    val venue: List<Venue>,
    val image: List<Images>,
)

data class Images(
    val url: String?,
    val blurhash: String?
)
data class Venue(
    val id: String,
    val name: String?,
    val shortDescription: String?,
    val address: String?,
    val country: String?,
)
