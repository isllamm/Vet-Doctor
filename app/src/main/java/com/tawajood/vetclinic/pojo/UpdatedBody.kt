package com.tawajood.vetclinic.pojo

import com.google.gson.annotations.SerializedName
import java.io.File

data class UpdatedBody(
    val name: String,
    val phone: String,
    @SerializedName("country_code")
    val countryCode: String,
    val email: String,
    val registration_number: String,
    val consultation_fees: String,
    val consultation_duration: String,
    val address: String,
    val details: String,
    val status_online: Int,
    val lat: String,
    val lng: String,
    val image: File?,
    val clinic_specializations: ArrayList<String>,
)