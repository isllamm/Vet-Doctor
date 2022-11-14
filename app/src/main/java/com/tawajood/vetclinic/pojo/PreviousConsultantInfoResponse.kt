package com.tawajood.vetclinic.pojo

import com.google.gson.annotations.SerializedName

data class PreviousConsultantInfoResponse(
    @SerializedName("previous_request")
    val previousConsultants: ConsultantInfo,
)