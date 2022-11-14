package com.tawajood.vetclinic.pojo

import com.google.gson.annotations.SerializedName

data class AnimalInfoResponse(
    @SerializedName("details_animal")
    val animalInfo: Pet,
    val request_id: String,
)