package com.tawajood.vetclinic.pojo

data class Clinic(
    val id: Int,
    val name: String,
    val image: String,
    val suspended_balance: Int,
    val valid_balance: Int
)