package com.tawajood.vetclinic.pojo

data class ReviewData(
    val id: Int,
    val user: User,
    val rate: Int,
    val comment: String,
)