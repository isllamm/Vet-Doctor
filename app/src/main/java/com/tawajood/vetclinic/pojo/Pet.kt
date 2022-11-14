package com.tawajood.vetclinic.pojo

data class Pet(
    val id: Int,
    val user_id: Int,
    val name: String,
    val image: String,
    val type_id: Int,
    val type: String,
    val gender:String,
    val age:Int,
    val weight:Int,
    val created_at:String,
    val previous_requests:MutableList<PreviousRequests>,
    val vaccinations:MutableList<Vaccination>
)