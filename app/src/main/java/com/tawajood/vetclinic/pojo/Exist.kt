package com.tawajood.vetclinic.pojo

import com.google.gson.annotations.SerializedName


data class Exist(
    @SerializedName("0")
    var message: String,
    var forgetcode:Int,
)