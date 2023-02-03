package com.clayenkitten.recipelibrary.model

class Recipe(
    val title: String,
    val description: String? = null,
    val imageUrl: String,
    val servings: String,
    val time: CookingTime,
    val review: ReviewSummary,
    val ingredients: List<Pair<String, String>>,
)

class ReviewSummary(
    val review_result: Float,
    val review_number: UInt,
)

class CookingTime (
    val overall_time: String,
    val active_time: String,
    val prepare_time: String,
)

val test_recipe = Recipe(
    "Cookies",
    "This chocolate chip cookie recipe is truly the best. Just take it from the 14,000 members of the Allrecipes community who have given it rave reviews! These chocolate chip cookies are beloved because they're soft, chewy, and absolutely irresistible. Our top-rated recipe for chocolate chip cookies will quickly become your go-to.",
    "https://images-gmi-pmc.edge-generalmills.com/087d17eb-500e-4b26-abd1-4f9ffa96a2c6.jpg",
    "32 cookies",
    CookingTime("90 min", "30 min", "5 min"),
    ReviewSummary(3.5f, 500u),
    listOf(
        Pair("salted butter softened", "1 cup"),
        Pair("white sugar", "1 cup"),
        Pair("pure vanilla extract", "2 tsp"),
        Pair("eggs", "2"),
        Pair("flour", "3 cups"),
        Pair("baking soda", "1 tsp"),
        Pair("chocolate chips", "2 cups"),
    )
)
