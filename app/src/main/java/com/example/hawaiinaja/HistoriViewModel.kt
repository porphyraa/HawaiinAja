package com.example.hawaiinaja

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HistoriViewModel : ViewModel() {
    private val listHistori = MutableLiveData<MutableList<HistoriModel>>()
    val listState = MutableLiveData<Long>()

    init {
        listHistori.value = mutableListOf<HistoriModel>()
        listState.value = 0L
    }

    fun getListHistori() = listHistori

    fun addHistori(h: HistoriModel) {
        listHistori.value!!.add(h)
        listState.value = System.currentTimeMillis()
    }
}