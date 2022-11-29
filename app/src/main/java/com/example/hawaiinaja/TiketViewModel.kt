package com.example.hawaiinaja

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class TiketViewModel : ViewModel() {
    private val listTiketHW = MutableLiveData<MutableList<TiketModel>>()
    private val listTiketMNP = MutableLiveData<MutableList<TiketModel>>()
    private val listTiketMSA = MutableLiveData<MutableList<TiketModel>>()
    private val listTiketMG = MutableLiveData<MutableList<TiketModel>>()

    init {
        listTiketHW.value = mutableListOf<TiketModel>()
        listTiketMNP.value = mutableListOf<TiketModel>()
        listTiketMSA.value = mutableListOf<TiketModel>()
        listTiketMG.value = mutableListOf<TiketModel>()

        listTiketHW.value!!.add(TiketModel(R.drawable.tiket_hw,"Tiket Hawai Waterpark - Weekday (Senin-Jumat)", "IDR 85.000","Tiket ini dapat dibeli 1 hari sebelum kedatangan. Tiket Sudah termasuk penggunaan semua wahana permainan air. Tinggi badan 85 cm keatas sudah dikenakan tiket penuh. Lansia diatas 60 thn masuk gratis."))
        listTiketHW.value!!.add(TiketModel(R.drawable.tiket_hw,"Tiket Hawai Waterpark - Weekend (Sabtu-Minggu)","IDR 110.000","Tiket ini dapat dibeli 1 hari sebelum kedatangan. Tiket ini sudah termasuk penggunaan semua wahana permainan air."))

        listTiketMNP.value!!.add(TiketModel(R.drawable.tiket_mnp,"Tiket Reguler MNP - Weekday (Senin-Kamis)","IDR 55,000","Tiket ini dapat dibeli 1 hari sebelum kedatangan. Semua wahana gratis kecuali Magic Journey, Adventure Land, Roemah147, dan Museum Ganesya"))
        listTiketMNP.value!!.add(TiketModel(R.drawable.tiket_mnp,"Tiket Reguler MNP - Weekend (Jumat-Minggu)","IDR 65,000","Tiket ini dapat dibeli 1 hari sebelum kedatangan. Semua wahana gratis kecuali Magic Journey, Adventure Land, Roemah147, dan Museum Ganesya"))

        listTiketMSA.value!!.add(TiketModel(R.drawable.tiket_msa,"Tiket Reguler MSA - Weekday (Senin-Jumat)","IDR 75,000","Tiket ini dapat dibeli 1 hari sebelum kedatangan. Tiket reguler sudah dapat menikmati semua wahana permainan kecuali : Carnival Game, Wildlife dan Human Clawn. Semua usia dikenakan tiket. Tidak ada free untuk pendamping"))
        listTiketMSA.value!!.add(TiketModel(R.drawable.tiket_msa,"Tiket Reguler MSA - Weekend (Sabtu-Minggu)","IDR 95,000","Tiket ini dapat dibeli 1 hari sebelum kedatangan. Tiket reguler sudah dapat menikmati semua wahana permainan kecuali : Carnival Game, Wildlife, dan Human Clawn. Semua usia dikenakan tiket. Tidak ada free untuk pendamping"))

        listTiketMG.value!!.add(TiketModel(R.drawable.tiket_mg,"Tiket Museum Ganesya","IDR 35,000","Tiket ini sudah dapat menikmati seluruh area di Museum Ganesya Malang"))
    }

    fun getListHW() = listTiketHW

    fun getListMNP() = listTiketMNP

    fun getListMSA() = listTiketMSA

    fun getListMG() = listTiketMG

}
