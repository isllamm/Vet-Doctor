package com.tawajood.vetclinic.ui.main.reports

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tawajood.vetclinic.pojo.BodyPartsResponse
import com.tawajood.vetclinic.pojo.NotificationResponse
import com.tawajood.vetclinic.pojo.ReportBody
import com.tawajood.vetclinic.repository.Repository
import com.tawajood.vetclinic.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import handleResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddReportViewModel
@Inject
constructor(
    private val repository: Repository
) : ViewModel() {

    private val _addReport = MutableStateFlow<Resource<Any>>(Resource.Idle())
    val addReport = _addReport.asSharedFlow()

    private val _bodyParts = MutableStateFlow<Resource<BodyPartsResponse>>(Resource.Idle())
    val bodyParts = _bodyParts.asSharedFlow()

    init {
        getBodyParts()
    }

    private fun getBodyParts() = viewModelScope.launch {
        try {
            _bodyParts.emit(Resource.Loading())
            val response = handleResponse(repository.getBodyParts())
            if (response.status) {
                _bodyParts.emit(Resource.Success(response.data!!))
            } else {
                _bodyParts.emit(Resource.Error(message = response.msg))
            }
        } catch (e: Exception) {
            _bodyParts.emit(Resource.Error(message = e.message!!))
        }
    }

    fun addReport(reportBody: ReportBody) = viewModelScope.launch {
        try {
            _addReport.emit(Resource.Loading())
            val response = handleResponse(repository.addReport(reportBody))
            if (response.status) {
                _addReport.emit(Resource.Success(response.data!!))
            } else {
                _addReport.emit(Resource.Error(message = response.msg))
            }
        } catch (e: Exception) {
            _addReport.emit(Resource.Error(message = e.message!!))
        }
    }
}
