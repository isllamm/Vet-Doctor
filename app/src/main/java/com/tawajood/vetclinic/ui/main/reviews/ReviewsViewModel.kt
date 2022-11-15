package com.tawajood.vetclinic.ui.main.reviews

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tawajood.vetclinic.pojo.NotificationResponse
import com.tawajood.vetclinic.pojo.ReviewsResponse
import com.tawajood.vetclinic.repository.Repository
import com.tawajood.vetclinic.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import handleResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReviewsViewModel
@Inject
constructor(
    private val repository: Repository
) : ViewModel() {
    private val _reviews = MutableStateFlow<Resource<ReviewsResponse>>(Resource.Idle())
    val reviews = _reviews.asSharedFlow()

    init {
        getReviews()
    }

    private fun getReviews() = viewModelScope.launch {
        try {
            _reviews.emit(Resource.Loading())
            val response = handleResponse(repository.getReviews())
            if (response.status) {
                _reviews.emit(Resource.Success(response.data!!))
            } else {
                _reviews.emit(Resource.Error(message = response.msg))
            }
        } catch (e: Exception) {
            _reviews.emit(Resource.Error(message = e.message!!))
        }
    }
}