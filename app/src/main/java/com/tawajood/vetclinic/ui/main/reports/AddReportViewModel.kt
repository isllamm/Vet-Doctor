package com.tawajood.vetclinic.ui.main.reports

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tawajood.vetclinic.pojo.NotificationResponse
import com.tawajood.vetclinic.repository.Repository
import com.tawajood.vetclinic.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
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

    private val _addReportViewModel = MutableStateFlow<Resource<Any>>(Resource.Idle())
    val addReportViewModel = _addReportViewModel.asSharedFlow()

    init {

    }


}
