package com.tawajood.vetclinic.repository

import PrefsHelper
import com.tawajood.vetclinic.api.RetrofitApi
import com.tawajood.vetclinic.pojo.RegisterBody
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
    suspend fun deleteAccount() =
        api.deleteAccount(PrefsHelper.getLanguage(), PrefsHelper.getToken())

    suspend fun getNotifications() =
        api.getNotifications(PrefsHelper.getLanguage(), PrefsHelper.getToken())

    suspend fun getPreviousConsultants() =
        api.getPreviousConsultants(PrefsHelper.getLanguage(), PrefsHelper.getToken())

    suspend fun getPreviousConsultantsInfo(id: String) =
        api.getPreviousConsultantsInfo(PrefsHelper.getLanguage(), PrefsHelper.getToken(), id)

    suspend fun getCurrentConsultants() =
        api.getCurrentConsultants(PrefsHelper.getLanguage(), PrefsHelper.getToken())

    suspend fun getCurrentConsultantsInfo(id: String) =
        api.getCurrentConsultantsInfo(PrefsHelper.getLanguage(), PrefsHelper.getToken(), id)

    suspend fun getNewConsultants() =
        api.getNewConsultants(PrefsHelper.getLanguage(), PrefsHelper.getToken())

    suspend fun getNewConsultantsInfo(id: String) =
        api.getNewConsultantsInfo(PrefsHelper.getLanguage(), PrefsHelper.getToken(), id)
}