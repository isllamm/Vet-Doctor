package com.tawajood.vetclinic.pojo

data class PreviousRequests(
    val id: Int,
    val date: String,
    val clinic_report: String,
    val clinic_id: Int,
    val clinic: Clinic,
    val medicines:MutableList<Medicine>,
)