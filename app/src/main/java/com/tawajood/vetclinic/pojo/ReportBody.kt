package com.tawajood.vetclinic.pojo

import com.google.gson.annotations.SerializedName

data class ReportBody(
    val request_id: Int,
    val report: String,
    val medicines: String,
    @SerializedName("cases")
    val cases: ArrayList<Int>,
)