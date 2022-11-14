package com.tawajood.vetclinic.pojo

data class Vaccination(
    val id: Int,
    val pet_id: Int,
    val date: String,
    val type_id: Int,
    val vaccinationType: String,
)