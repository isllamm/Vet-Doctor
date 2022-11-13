package com.tawajood.vetclinic.pojo

import com.google.gson.annotations.SerializedName

data class Consultant(
    val id: Int,
    val created_at: String,
    val type_id: Int,
    @SerializedName("request_type")
    val consultantType: ConsultantType,

    )