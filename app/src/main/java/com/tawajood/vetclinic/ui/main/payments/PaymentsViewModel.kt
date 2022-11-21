package com.tawajood.vetclinic.ui.main.payments

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tawajood.vetclinic.pojo.ContactUsResponse
import com.tawajood.vetclinic.pojo.PaymentsResponse
import com.tawajood.vetclinic.pojo.WithdrawResponse
import com.tawajood.vetclinic.repository.Repository
import com.tawajood.vetclinic.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import handleResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PaymentsViewModel
@Inject
constructor(
    private val repository: Repository
) : ViewModel() {

    private val _money = MutableStateFlow<Resource<PaymentsResponse>>(Resource.Idle())
    val money = _money.asSharedFlow()

    private val _withdraw = MutableStateFlow<Resource<WithdrawResponse>>(Resource.Idle())
    val withdraw = _withdraw.asSharedFlow()

    init {
        getMoney()
    }

    private fun getMoney() = viewModelScope.launch {
        try {
            _money.emit(Resource.Loading())
            val response = handleResponse(repository.getWithdraws())
            if (response.status) {
                _money.emit(Resource.Success(response.data!!))
            } else {
                _money.emit(Resource.Error(message = response.msg))
            }
        } catch (e: Exception) {
            _money.emit(Resource.Error(message = e.message!!))
        }
    }

    fun withdrawMoney(
        price: String,
        accountOwnerName: String,
        bankId: String,
        accountNumber: String
    ) = viewModelScope.launch {
        try {
            _withdraw.emit(Resource.Loading())
            val response =
                handleResponse(repository.withdraw(price, accountOwnerName, bankId, accountNumber))
            if (response.status) {
                _withdraw.emit(Resource.Success(response.data!!))
            } else {
                _withdraw.emit(Resource.Error(message = response.msg))
            }
        } catch (e: Exception) {
            _withdraw.emit(Resource.Error(message = e.message!!))
        }
    }
}