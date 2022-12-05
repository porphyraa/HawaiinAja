package com.example.hawaiinaja

import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.hawaiinaja.databinding.ActivityTiketSayaBinding
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.time.LocalDate

class TiketSaya : AppCompatActivity() {

    private lateinit var binding: ActivityTiketSayaBinding

    private lateinit var container: SharedPreferences
    private val fragButtonUnlogin = MainButtonUnlogin()
    private val fragButtonLogin = MainButtonLogin()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTiketSayaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        container = getSharedPreferences("CONNECT", Context.MODE_PRIVATE)
        var connect = container.getBoolean("LOGIN", false)
        setMainButton(connect)

        val rvPaidTiket = findViewById<RecyclerView>(R.id.daftarPaidTiketRV)

        val vPaidModel = ViewModelProvider(this).get(PaidTiketViewModel::class.java)
        val vHistoriModel = ViewModelProvider(this).get(HistoriViewModel::class.java)

        val serializedPaid = container.getString("paidtiket", null)
        val typePaid = object : TypeToken<MutableList<PaidTiketModel>>() {}.type
        val listPaidTiket = Gson().fromJson<MutableList<PaidTiketModel>>(serializedPaid, typePaid)

        val serializedUser = container.getString("histori", null)
        val type = object : TypeToken<MutableList<HistoriModel>>() {}.type
        val listHistori = Gson().fromJson<MutableList<HistoriModel>>(serializedUser, type)

        if (!serializedUser.isNullOrEmpty()) {
            listHistori.forEach { p ->
                vHistoriModel.addHistori(HistoriModel(p.foto, p.nama, p.harga, p.tanggal))
            }
        }

        if (!serializedPaid.isNullOrEmpty()) {
            listPaidTiket.forEach { p ->
                if (isExpired(p.tanggal)) {
                    vHistoriModel.addHistori(HistoriModel(p.foto, p.nama, p.harga, p.tanggal))
                } else {
                    vPaidModel.addPaidTiket(PaidTiketModel(p.foto, p.nama, p.harga, p.tanggal))
                }
            }
        }

        vPaidModel.listState.observe(this, Observer {
            rvPaidTiket.adapter = PaidTiketAdapter(vPaidModel.getListPaidTiket().value!!)
            rvPaidTiket.layoutManager = LinearLayoutManager(this)

            val editor = container.edit()
            val listJson = Gson().toJson(vPaidModel.getListPaidTiket().value)
            editor.putString("paidtiket", listJson)
            editor.commit()
        })

        vHistoriModel.listState.observe(this, Observer {
            val editor = container.edit()
            val listJson2 = Gson().toJson(vHistoriModel.getListHistori().value)
            editor.putString("histori", listJson2)
            editor.commit()
        })
    }

    companion object {
        @RequiresApi(Build.VERSION_CODES.O)
        fun isExpired(date: String): Boolean {
            val validDate = date.split("/").toTypedArray()
            val day = validDate[0].toInt()
            val month = validDate[1].toInt()
            val year = validDate[2].toInt()
            val totalDay = LocalDate.of(year, month, day).dayOfYear
            val now = LocalDate.now().dayOfYear

            return totalDay < now
        }
    }

    private fun setMainButton(isConnected: Boolean) {
        if (isConnected) {
            supportFragmentManager.beginTransaction().apply {
                replace(R.id.frameTiketSaya, fragButtonLogin)
                commit()
            }
        } else {
            supportFragmentManager.beginTransaction().apply {
                replace(R.id.frameTiketSaya, fragButtonUnlogin)
                commit()
            }
        }
    }
}