package com.tawajood.vetclinic.pojo

import com.google.gson.annotations.SerializedName

data class EditProfileResponse(
    @SerializedName("clinic")
    val profile: ProfileResponse,
    val specializations: MutableList<SpecializationName>,
    val days: MutableList<Day>,
)