package com.tawajood.vetclinic.pojo

data class Consultants(
    val current_page: Int,
    val data: MutableList<Consultant>,
    val first_page_url: String,
    val from: Int,
    val last_page: Int,
)