package com.tawajood.vetclinic.repository

import PrefsHelper
import com.tawajood.vetclinic.api.RetrofitApi
import com.tawajood.vetclinic.pojo.MainResponse
import com.tawajood.vetclinic.pojo.RegisterBody
import com.tawajood.vetclinic.pojo.UpdatedBody
import com.tawajood.vetclinic.utils.toMap
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Response
import javax.inject.Inject

class Repository
@Inject
constructor(private val api: RetrofitApi) {


    suspend fun register(registerBody: RegisterBody) =
        api.register(PrefsHelper.getLanguage(), registerBody)

    suspend fun login(countryCode: String, phone: String, password: String) =
        api.login(PrefsHelper.getLanguage(), countryCode, phone, password)

    suspend fun checkPhone(countryCode: String, phone: String, security: String) =
        api.checkPhone(PrefsHelper.getLanguage(), countryCode, phone, security)

    suspend fun forgetPassword(
        countryCode: String,
        phone: String,
        password: String,
        forgetcode: String,
        security: String
    ) =
        api.forgetPassword(
            PrefsHelper.getLanguage(),
            countryCode,
            phone,
            password,
            forgetcode,
            security
        )

    suspend fun aboutUs() = api.aboutUs(PrefsHelper.getLanguage())
    suspend fun terms() = api.terms(PrefsHelper.getLanguage())
    suspend fun contact() = api.contactUs(PrefsHelper.getLanguage())

    suspend fun getWithdraws() = api.getWithdraws(PrefsHelper.getLanguage(), PrefsHelper.getToken())

    suspend fun withdraw(
        price: String,
        accountOwnerName: String,
        bankId: String,
        accountNumber: String
    ) = api.withdraw(
        PrefsHelper.getLanguage(),
        PrefsHelper.getToken(),
        price,
        accountOwnerName,
        bankId,
        accountNumber
    )

    suspend fun getProfile() = api.getProfile(PrefsHelper.getLanguage(), PrefsHelper.getToken())
    suspend fun getEditProfile() =
        api.getEditProfile(PrefsHelper.getLanguage(), PrefsHelper.getToken())

    suspend fun updateProfile(
        updateBody: UpdatedBody,
    ): Response<MainResponse<Any>> {
        if (updateBody.image != null) {
            val imagePart = MultipartBody.Part.createFormData(
                "image",
                updateBody.image.name,
                updateBody.image.asRequestBody("multipart/form-data".toMediaTypeOrNull())
            )

            return api.updateProfile(
                PrefsHelper.getToken(),
                PrefsHelper.getLanguage(),
                updateBody.toMap(),
                imagePart,
                updateBody.clinic_specializations
            )
        } else {
            return api.updateProfile(
                PrefsHelper.getToken(),
                PrefsHelper.getLanguage(),
                updateBody
            )
        }
    }

    suspend fun deleteAccount() =
        api.deleteAccount(PrefsHelper.getLanguage(), PrefsHelper.getToken())

    suspend fun getNotifications() =
        api.getNotifications(PrefsHelper.getLanguage(), PrefsHelper.getToken())

    suspend fun getPreviousConsultants() =
        api.getPreviousConsultants(PrefsHelper.getLanguage(), PrefsHelper.getToken())

    suspend fun getPreviousConsultantsInfo(id: String) =
        api.getPreviousConsultantsInfo(PrefsHelper.getLanguage(), PrefsHelper.getToken(), id)

    suspend fun getPreviousAnimalInfo(id: String) =
        api.getPreviousAnimalInfo(PrefsHelper.getLanguage(), PrefsHelper.getToken(), id)

    suspend fun getCurrentConsultants() =
        api.getCurrentConsultants(PrefsHelper.getLanguage(), PrefsHelper.getToken())

    suspend fun getCurrentConsultantsInfo(id: String) =
        api.getCurrentConsultantsInfo(PrefsHelper.getLanguage(), PrefsHelper.getToken(), id)

    suspend fun getNewConsultants() =
        api.getNewConsultants(PrefsHelper.getLanguage(), PrefsHelper.getToken())

    suspend fun getNewConsultantsInfo(id: String) =
        api.getNewConsultantsInfo(PrefsHelper.getLanguage(), PrefsHelper.getToken(), id)

    suspend fun getNewAnimalInfo(id: String) =
        api.getNewAnimalInfo(PrefsHelper.getLanguage(), PrefsHelper.getToken(), id)


    suspend fun getReviews() =
        api.getReviews(PrefsHelper.getLanguage(), PrefsHelper.getToken())
}