package com.tawajood.vetclinic.ui.main.notifications

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tawajood.vetclinic.pojo.NotificationResponse
import com.tawajood.vetclinic.repository.Repository
import com.tawajood.vetclinic.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import handleResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotificationsViewModel
@Inject
constructor(
    private val repository: Repository
) : ViewModel() {
    private val _notifications = MutableStateFlow<Resource<NotificationResponse>>(Resource.Idle())
    val notifications = _notifications.asSharedFlow()

    init {
        getNotifications()
    }

    private fun getNotifications() = viewModelScope.launch {
        try {
            _notifications.emit(Resource.Loading())
            val response = handleResponse(repository.getNotifications())
            if (response.status) {
                _notifications.emit(Resource.Success(response.data!!))
            } else {
                _notifications.emit(Resource.Error(message = response.msg))
            }
        } catch (e: Exception) {
            _notifications.emit(Resource.Error(message = e.message!!))
        }
    }
}