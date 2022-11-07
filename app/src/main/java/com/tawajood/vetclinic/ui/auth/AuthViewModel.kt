package com.tawajood.vetclinic.ui.auth

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

    private val _userLoginFlow = MutableSharedFlow<Resource<User>>()
    val userLoginFlow = _userLoginFlow.asSharedFlow()

    private val _userRegisterFlow = MutableSharedFlow<Resource<User>>()
    val userRegisterFlow = _userRegisterFlow.asSharedFlow()

    private val _checkPhone = MutableStateFlow<Resource<Exist>>(Resource.Idle())
    val checkPhone = _checkPhone.asSharedFlow()

    private val _check = MutableStateFlow<Resource<Check>>(Resource.Idle())
    val check = _check.asSharedFlow()

    fun login(countryCode: String, phone: String, password: String) = viewModelScope.launch {
        try {
            _userLoginFlow.emit(Resource.Loading())
            val response = handleResponse(repository.login(countryCode, phone, password))
            if (response.status) {
                _userLoginFlow.emit(Resource.Success(response.data!!.user))
            } else {
                _userLoginFlow.emit(Resource.Error(message = response.msg))
            }
        } catch (e: Exception) {
            _userLoginFlow.emit(Resource.Error(message = e.message.toString()))
        }
    }


    fun register(registerBody: RegisterBody) = viewModelScope.launch {
        try {
            _userRegisterFlow.emit(Resource.Loading())
            val response = handleResponse(repository.register(registerBody))
            if (response.status) {
                _userRegisterFlow.emit(Resource.Success(response.data!!.user))
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
            val response = handleResponse(repository.checkPhone(countryCode, phone,"***"))
            if (response.status) {
                _checkPhone.emit(Resource.Success(response.data!!))
            } else {
                _checkPhone.emit(Resource.Error(message = response.msg))
            }
        } catch (e: Exception) {
            _checkPhone.emit(Resource.Error(message = e.message!!))
        }
    }
}