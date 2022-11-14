package com.tawajood.vetclinic.pojo

import com.google.gson.annotations.SerializedName

data class NewConsultantInfoResponse (
    @SerializedName("new_requests")
    val newConsultants: ConsultantInfo,
)