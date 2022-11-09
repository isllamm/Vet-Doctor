package com.tawajood.vetclinic.pojo

data class PaymentsResponse(
    val clinic: Clinic,
    val banks: MutableList<Bank>
)