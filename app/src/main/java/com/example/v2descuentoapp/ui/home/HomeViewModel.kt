package com.example.v2descuentoapp.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HomeViewModel : ViewModel() {

    private val _taxIncluded = MutableLiveData(false)
    val taxIncluded: LiveData<Boolean> get() = _taxIncluded

    private val _listPrice = MutableLiveData("")
    val listPrice: LiveData<String> get() = _listPrice

    private val _discount = MutableLiveData("")
    val discount: LiveData<String> get() = _discount

    private val _discountValue = MutableLiveData("")
    val discountValue: LiveData<String> get() = _discountValue

    private val _subtotalValue = MutableLiveData("")
    val subtotalValue: LiveData<String> get() = _subtotalValue

    private val _taxValue = MutableLiveData("")
    val taxValue: LiveData<String> get() = _taxValue

    private val _totalValue = MutableLiveData("")
    val totalValue: LiveData<String> get() = _totalValue

    fun setTaxIncluded(value: Boolean) {
        _taxIncluded.value = value
    }

    fun updateValues(listPrice: String, discount: String, discountAmount: String, subtotal: String, tax: String, total: String) {
        _listPrice.value = listPrice
        _discount.value = discount
        _discountValue.value = discountAmount
        _subtotalValue.value = subtotal
        _taxValue.value = tax
        _totalValue.value = total
    }
}
