package com.tawajood.vetclinic.pojo

data class Pet(
    val id: Int,
    val user_id: Int,
    val name: String,
    val image: String,
    val type_id: Int,
    val type: String,
)