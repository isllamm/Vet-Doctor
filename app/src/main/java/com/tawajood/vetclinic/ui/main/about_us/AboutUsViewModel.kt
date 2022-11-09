package com.tawajood.vetclinic.ui.main.about_us

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tawajood.vetclinic.pojo.About
import com.tawajood.vetclinic.pojo.Exist
import com.tawajood.vetclinic.repository.Repository
import com.tawajood.vetclinic.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import handleResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AboutUsViewModel
@Inject
constructor(
    private val repository: Repository
) : ViewModel() {
    private val _about = MutableStateFlow<Resource<About>>(Resource.Idle())
    val about = _about.asSharedFlow()

    init {
        getAboutUs()
    }

    private fun getAboutUs() = viewModelScope.launch {
        try {
            _about.emit(Resource.Loading())
            val response = handleResponse(repository.aboutUs())
            if (response.status) {
                _about.emit(Resource.Success(response.data!!))
            } else {
                _about.emit(Resource.Error(message = response.msg))
            }
        } catch (e: Exception) {
            _about.emit(Resource.Error(message = e.message!!))
        }
    }
}