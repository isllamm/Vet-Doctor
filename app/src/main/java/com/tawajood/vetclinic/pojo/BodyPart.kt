package com.tawajood.vetclinic.pojo

import com.google.gson.annotations.SerializedName

data class BodyPart(
    val id: Int,
    @SerializedName("name ")
    val name: String,
    var status: Int = 0,
)