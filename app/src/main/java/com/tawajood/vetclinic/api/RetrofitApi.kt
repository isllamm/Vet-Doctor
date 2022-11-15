package com.tawajood.vetclinic.api


import com.tawajood.vetclinic.pojo.*
import okhttp3.MultipartBody
import okhttp3.RequestBody

import retrofit2.Response
import retrofit2.http.*

interface RetrofitApi {

    companion object {
        const val BASE_URL = "https://vet.horizon.net.sa/api-clinic/"
    }

    @POST("auth/register")
    suspend fun register(
        @Header("lang") lang: String,
        @Body registerBody: RegisterBody
    ): Response<MainResponse<Token>>

    @FormUrlEncoded
    @POST("auth/login")
    suspend fun login(
        @Header("lang") lang: String,
        @Field("country_code") countryCode: String,
        @Field("phone") phone: String,
        @Field("password") password: String
    ): Response<MainResponse<Token>>


    @FormUrlEncoded
    @POST("auth/check-phone")
    suspend fun checkPhone(
        @Header("lang") lang: String,
        @Field("country_code") countryCode: String,
        @Field("phone") phone: String,
        @Field("security") security: String,
    ): Response<MainResponse<Exist>>

    @FormUrlEncoded
    @POST("auth/forget-password")
    suspend fun forgetPassword(
        @Header("lang") lang: String,
        @Field("country_code") countryCode: String,
        @Field("phone") phone: String,
        @Field("password") password: String,
        @Field("forgetcode") forgetcode: String,
        @Field("security") security: String,
    ): Response<MainResponse<Any>>


    @GET("footer/about-us")
    suspend fun aboutUs(
        @Header("lang") lang: String,
    ): Response<MainResponse<About>>

    @GET("footer/terms")
    suspend fun terms(
        @Header("lang") lang: String,
    ): Response<MainResponse<Terms>>

    @GET("footer/contact-us")
    suspend fun contactUs(
        @Header("lang") lang: String,
    ): Response<MainResponse<ContactUsResponse>>

    @GET("profile/withdraw/withdraw-show")
    suspend fun getWithdraws(
        @Header("lang") lang: String,
        @Header("token") token: String,
    ): Response<MainResponse<PaymentsResponse>>

    @FormUrlEncoded
    @POST("profile/withdraw/withdraw-money")
    suspend fun withdraw(
        @Header("lang") lang: String,
        @Header("token") token: String,
        @Field("price") price: String,
        @Field("account_owner_name") account_owner_name: String,
        @Field("bank_id") bank_id: String,
        @Field("account_number") account_number: String,
    ): Response<MainResponse<Any>>


    @GET("profile/profile/show")
    suspend fun getProfile(
        @Header("lang") lang: String,
        @Header("token") token: String,
    ): Response<MainResponse<ProfileResponse>>


    @POST("profile/profile/update")
    suspend fun updateProfile(
        @Header("lang") lang: String,
        @Header("token") token: String,
        @Body updatedBody: UpdatedBody
    ): Response<MainResponse<Any>>

    @GET("profile/profile/delete-account")
    suspend fun deleteAccount(
        @Header("lang") lang: String,
        @Header("token") token: String,
    ): Response<MainResponse<Any>>

    @GET("profile/notifications/show")
    suspend fun getNotifications(
        @Header("lang") lang: String,
        @Header("token") token: String,
    ): Response<MainResponse<NotificationResponse>>

    @GET("profile/requests/previous-show")
    suspend fun getPreviousConsultants(
        @Header("lang") lang: String,
        @Header("token") token: String,
    ): Response<MainResponse<PreviousConsultantsResponse>>

    @GET("profile/requests/previous-details/{id}")
    suspend fun getPreviousConsultantsInfo(
        @Header("lang") lang: String,
        @Header("token") token: String,
        @Path("id") id: String,
    ): Response<MainResponse<PreviousConsultantInfoResponse>>

    @GET("profile/requests/current-show")
    suspend fun getCurrentConsultants(
        @Header("lang") lang: String,
        @Header("token") token: String,
    ): Response<MainResponse<CurrentConsultantsResponse>>

    @GET("profile/requests/current-details/{id}")
    suspend fun getCurrentConsultantsInfo(
        @Header("lang") lang: String,
        @Header("token") token: String,
        @Path("id") id: String,
    ): Response<MainResponse<CurrentConsultantInfoResponse>>

    @GET("profile/requests/new-show")
    suspend fun getNewConsultants(
        @Header("lang") lang: String,
        @Header("token") token: String,
    ): Response<MainResponse<NewConsultantsResponse>>

    @GET("profile/requests/new-details/{id}")
    suspend fun getNewConsultantsInfo(
        @Header("lang") lang: String,
        @Header("token") token: String,
        @Path("id") id: String,
    ): Response<MainResponse<NewConsultantInfoResponse>>

    @GET("profile/requests/previous-details-animal/{id}/1")
    suspend fun getPreviousAnimalInfo(
        @Header("lang") lang: String,
        @Header("token") token: String,
        @Path("id") id: String,
    ): Response<MainResponse<AnimalInfoResponse>>

    @GET("profile/requests/new-details-animal/{id}/1")
    suspend fun getNewAnimalInfo(
        @Header("lang") lang: String,
        @Header("token") token: String,
        @Path("id") id: String,
    ): Response<MainResponse<AnimalInfoResponse>>

    @GET("reviews/show")
    suspend fun getReviews(
        @Header("lang") lang: String,
        @Header("token") token: String,
    ): Response<MainResponse<ReviewsResponse>>

}