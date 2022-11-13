package com.tawajood.vetclinic.pojo

import com.google.gson.annotations.SerializedName

data class CurrentConsultantsResponse(
    @SerializedName("current_requests")
    val currentConsultants: Consultants
)