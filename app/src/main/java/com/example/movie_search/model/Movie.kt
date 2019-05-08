package com.example.movie_search.model

import com.google.gson.annotations.SerializedName




data class Movie(
    @SerializedName("title") val title: String,
    @SerializedName("image") val imageUrl: String,
    @SerializedName("subtitle") val subTitle: String,
    @SerializedName("director") var director: String,
    @SerializedName("actor") var actor: String,
    @SerializedName("userRating") val userRating: String
)


