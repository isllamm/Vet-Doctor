package com.tawajood.vetclinic.api


import com.tawajood.vetclinic.pojo.Exist
import com.tawajood.vetclinic.pojo.MainResponse
import com.tawajood.vetclinic.pojo.RegisterBody
import com.tawajood.vetclinic.pojo.UserResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody

import retrofit2.Response
import retrofit2.http.*

interface RetrofitApi {

    companion object {
        const val BASE_URL = "https://vet.horizon.net.sa/api-clinic/"
    }

    @FormUrlEncoded
    @POST("auth/register")
    suspend fun register(
        @Header("lang") lang: String,
        @Body registerBody: RegisterBody
    ): Response<MainResponse<UserResponse>>

    @FormUrlEncoded
    @POST("auth/login")
    suspend fun login(
        @Header("lang") lang: String,
        @Field("country_code") countryCode: String,
        @Field("phone") phone: String,
        @Field("password") password: String
    ): Response<MainResponse<UserResponse>>


    @FormUrlEncoded
    @POST("auth/check-phone")
    suspend fun checkPhone(
        @Header("lang") lang: String,
        @Field("country_code") countryCode: String,
        @Field("phone") phone: String,
        @Field("security") security: String,
    ): Response<MainResponse<Exist>>


}