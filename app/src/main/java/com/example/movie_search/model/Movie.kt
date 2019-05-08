package com.example.movie_search.model

import com.google.gson.annotations.SerializedName




data class Movie(
    val title: String,
    @SerializedName("image")  val imageUrl: String,
    val subTitle: String,
    var director: String,
    var actor: String,
    val userRating: String
)


