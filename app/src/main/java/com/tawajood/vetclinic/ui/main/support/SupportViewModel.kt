package com.tawajood.vetclinic.ui.main.support

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tawajood.vetclinic.pojo.About
import com.tawajood.vetclinic.pojo.ContactUsResponse
import com.tawajood.vetclinic.repository.Repository
import com.tawajood.vetclinic.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import handleResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SupportViewModel
@Inject
constructor(
    private val repository: Repository
) : ViewModel() {
    private val _contact = MutableStateFlow<Resource<ContactUsResponse>>(Resource.Idle())
    val contact = _contact.asSharedFlow()

    init {
        getContactUs()
    }

    private fun getContactUs() = viewModelScope.launch {
        try {
            _contact.emit(Resource.Loading())
            val response = handleResponse(repository.contact())
            if (response.status) {
                _contact.emit(Resource.Success(response.data!!))
            } else {
                _contact.emit(Resource.Error(message = response.msg))
            }
        } catch (e: Exception) {
            _contact.emit(Resource.Error(message = e.message!!))
        }
    }
}