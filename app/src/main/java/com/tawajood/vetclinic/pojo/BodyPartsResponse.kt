package com.tawajood.vetclinic.pojo

import com.google.gson.annotations.SerializedName

data class BodyPartsResponse(
    @SerializedName("parts-body")
    val bodyParts: MutableList<BodyPart>,
)