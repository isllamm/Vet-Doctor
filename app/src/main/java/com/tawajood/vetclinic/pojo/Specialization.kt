package com.tawajood.vetclinic.pojo

data class Specialization(
    val id: Int,
    val clinic_id: Int,
    val specialization_id: Int,
    val specialization: SpecializationName,
)