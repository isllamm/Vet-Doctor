package com.tawajood.vetclinic.pojo

import com.google.gson.annotations.SerializedName

data class RegisterBody(
    var name: String,
    @SerializedName("country_code")
    var countryCode: String,
    var phone: String,
    var email: String,
    var address: String,
    @SerializedName("registration number")
    var registrationNumber: String,
    var password: String,
)