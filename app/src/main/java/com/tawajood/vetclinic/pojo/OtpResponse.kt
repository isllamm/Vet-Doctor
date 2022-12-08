package com.tawajood.vetclinic.pojo

data class OtpResponse(
    val Code: String,
    val MessageIs: String,
    val sent_code: String,
    val status: Boolean,
)