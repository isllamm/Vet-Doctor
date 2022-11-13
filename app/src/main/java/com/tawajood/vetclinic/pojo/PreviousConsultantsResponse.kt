package com.tawajood.vetclinic.pojo

import com.google.gson.annotations.SerializedName

data class PreviousConsultantsResponse(

    @SerializedName("previous_requests")
    val previousConsultants: Consultants
)