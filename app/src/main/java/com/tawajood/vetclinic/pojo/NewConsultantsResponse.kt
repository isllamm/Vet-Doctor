package com.tawajood.vetclinic.pojo

import com.google.gson.annotations.SerializedName

data class NewConsultantsResponse (
    @SerializedName("new_requests")
    val newConsultants: Consultants
        )