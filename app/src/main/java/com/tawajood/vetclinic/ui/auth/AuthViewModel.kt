package com.tawajood.vetclinic.ui.auth

import android.util.Log
import com.tawajood.vetclinic.repository.Repository
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tawajood.vetclinic.pojo.*
import com.tawajood.vetclinic.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import handleResponse
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel
@Inject
constructor(
    private val repository: Repository
) : ViewModel() {

    private val _userLoginFlow = MutableSharedFlow<Resource<Token>>()
    val userLoginFlow = _userLoginFlow.asSharedFlow()

    private val _userRegisterFlow = MutableSharedFlow<Resource<Token>>()
    val userRegisterFlow = _userRegisterFlow.asSharedFlow()

    private val _checkPhone = MutableSharedFlow<Resource<Exist>>()
    val checkPhone = _checkPhone.asSharedFlow()

    private val _forgetPassword = MutableStateFlow<Resource<Any>>(Resource.Idle())
    val forgetPassword = _forgetPassword.asSharedFlow()

    private val _sendOtp = MutableStateFlow<Resource<OtpResponse>>(Resource.Idle())
    val sendOtp = _sendOtp.asSharedFlow()


    fun login(countryCode: String, phone: String, password: String) = viewModelScope.launch {
        try {
            _userLoginFlow.emit(Resource.Loading())
            val response = handleResponse(repository.login(countryCode, phone, password))
            if (response.status) {
                _userLoginFlow.emit(Resource.Success(response.data!!))
            } else {
                _userLoginFlow.emit(Resource.Error(message = response.msg))
                Log.d("islam", response.msg)

            }
        } catch (e: Exception) {
            Log.d("islam", e.message.toString())
            _userLoginFlow.emit(Resource.Error(message = e.message.toString()))
        }
    }


    fun register(registerBody: RegisterBody) = viewModelScope.launch {
        try {
            _userRegisterFlow.emit(Resource.Loading())
            val response = handleResponse(repository.register(registerBody))
            if (response.status) {
                _userRegisterFlow.emit(Resource.Success(response.data!!))
            } else {
                _userRegisterFlow.emit(Resource.Error(message = response.msg))
            }
        } catch (e: Exception) {
            _userRegisterFlow.emit(Resource.Error(message = e.message.toString()))
        }
    }


    fun checkPhone(countryCode: String, phone: String) = viewModelScope.launch {
        try {
            _checkPhone.emit(Resource.Loading())
            val response = handleResponse(repository.checkPhone(countryCode, phone, "***"))
            if (response.status) {
                _checkPhone.emit(Resource.Success(response.data!!))
            } else {
                _checkPhone.emit(Resource.Error(message = response.msg))
            }
        } catch (e: Exception) {
            _checkPhone.emit(Resource.Error(message = e.message!!))
        }
    }

    fun sendOtp(countryCode: String, phone: String) = viewModelScope.launch {
        try {
            _sendOtp.emit(Resource.Loading())
            val response = repository.sendOtp(countryCode, phone)
            if (response.isSuccessful) {
                if (response.body()!!.status) {
                    _sendOtp.emit(Resource.Success(response.body()!!))
                } else {
                    _sendOtp.emit(Resource.Success(response.body()!!))
                    //_sendOtp.emit(Resource.Error(message = response.body()!!.MessageIs))
                }
            } else {
                //_sendOtp.emit(Resource.Error(message = response.message()))
            }
        } catch (e: Exception) {
            _sendOtp.emit(Resource.Error(message = e.message!!))
        }
    }

    fun forgetPassword(
        countryCode: String,
        phone: String,
        password: String,
        forgetcode: String,
    ) = viewModelScope.launch {
        try {
            _forgetPassword.emit(Resource.Loading())
            val response = handleResponse(
                repository.forgetPassword(
                    countryCode,
                    phone,
                    password,
                    forgetcode,
                    "***"
                )
            )
            if (response.status) {
                _forgetPassword.emit(Resource.Success(response.data!!))
            } else {
                _forgetPassword.emit(Resource.Error(message = response.msg))
            }
        } catch (e: Exception) {
            _forgetPassword.emit(Resource.Error(message = e.message!!))
        }
    }
}