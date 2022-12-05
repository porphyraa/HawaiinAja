package com.example.hawaiinaja

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class UnpaidTiketViewModel : ViewModel() {
    private val listUnpaidTiket = MutableLiveData<MutableList<UnpaidTiketModel>>()
    val listState = MutableLiveData<Long>()

    init {
        listUnpaidTiket.value = mutableListOf<UnpaidTiketModel>()
        listState.value = 0L
    }

    fun getListUnpaidTiket() = listUnpaidTiket

    fun addUnpaidTiket(un: UnpaidTiketModel) {
        listUnpaidTiket.value!!.add(un)
    }

    fun removeUnpaidTiket(i: Int) {
        listUnpaidTiket.value!!.removeAt(i)
        listState.value = System.currentTimeMillis()
    }

    fun removeAllUnpaidTiket() {
        listUnpaidTiket.value!!.clear()
        listState.value = System.currentTimeMillis()
    }
}
