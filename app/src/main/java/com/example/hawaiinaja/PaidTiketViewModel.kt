package com.example.hawaiinaja

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class PaidTiketViewModel : ViewModel() {
    private val listPaidTiket = MutableLiveData<MutableList<PaidTiketModel>>()
    val listState = MutableLiveData<Long>()

    init {
        listPaidTiket.value = mutableListOf<PaidTiketModel>()
        listState.value = 0L
    }

    fun getListPaidTiket() = listPaidTiket

    fun addPaidTiket(p: PaidTiketModel) {
        listPaidTiket.value!!.add(p)
        listState.value = System.currentTimeMillis()
    }
}
