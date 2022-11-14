package com.tawajood.vetclinic.pojo

data class ConsultantInfo(
    val id: Int,
    val date: String,
    val time: String,
    val requestType: String,
    val pet_id: Int,
    val pet: Pet,
    val notes: String,
    val images: MutableList<ImageClinic>,
    val clinic_report: String,
    val accepted: Int,
    val status: Int,
    val statusName: String,
    val paid: Int,
    val address: String,
    val lat: String,
    val lng: String,
    val type_id: Int,

    )