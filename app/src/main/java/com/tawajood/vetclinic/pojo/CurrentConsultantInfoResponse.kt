package com.tawajood.vetclinic.pojo

import com.google.gson.annotations.SerializedName

data class CurrentConsultantInfoResponse(
    @SerializedName("current_request")
    val currentConsultants: ConsultantInfo,
)