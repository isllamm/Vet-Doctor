package com.tawajood.vetclinic.pojo

import com.google.gson.annotations.SerializedName

data class Review(
    @SerializedName("data")
    val reviews: MutableList<ReviewData>,
)