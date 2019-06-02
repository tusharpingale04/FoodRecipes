package com.tushar.foodrecipesmvvm.model

data class Recipe(
    val f2f_url: String,
    val image_url: String,
    val publisher: String,
    val publisher_url: String,
    val recipe_id: String,
    val social_rank: Double,
    val source_url: String,
    val title: String
)