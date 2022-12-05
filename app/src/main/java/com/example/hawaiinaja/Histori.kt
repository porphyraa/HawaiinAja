package com.example.hawaiinaja

import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.hawaiinaja.databinding.ActivityHistoriBinding
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Histori : AppCompatActivity() {

    private lateinit var binding: ActivityHistoriBinding

    private lateinit var container: SharedPreferences
    private val fragButtonUnlogin = MainButtonUnlogin()
    private val fragButtonLogin = MainButtonLogin()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoriBinding.inflate(layoutInflater)
        setContentView(binding.root)

        container = getSharedPreferences("CONNECT", Context.MODE_PRIVATE)
        var connect = container.getBoolean("LOGIN", false)
        setMainButton(connect)

        val rvHistori = findViewById<RecyclerView>(R.id.daftarHistoriTiketRV)
        val vModel = ViewModelProvider(this).get(HistoriViewModel::class.java)
        val vPaidModel = ViewModelProvider(this).get(PaidTiketViewModel::class.java)

        val serializedPaid = container.getString("paidtiket", null)
        val typePaid = object : TypeToken<MutableList<PaidTiketModel>>() {}.type
        val listPaidTiket = Gson().fromJson<MutableList<PaidTiketModel>>(serializedPaid, typePaid)

        val serializedUser = container.getString("histori", null)
        val type = object : TypeToken<MutableList<HistoriModel>>() {}.type
        val listHistori = Gson().fromJson<MutableList<HistoriModel>>(serializedUser, type)

        if (serializedUser.isNullOrEmpty()) {
            binding.textHistoriNoTicket.text = "Tidak ada riwayat pemesanan"
        } else {
            binding.textHistoriNoTicket.text = ""
            listHistori.forEach { p ->
                vModel.addHistori(HistoriModel(p.foto, p.nama, p.harga, p.tanggal))
            }
        }

        if (!serializedPaid.isNullOrEmpty()) {
            listPaidTiket.forEach { p ->
                if (TiketSaya.isExpired(p.tanggal)) {
                    vModel.addHistori(HistoriModel(p.foto, p.nama, p.harga, p.tanggal))
                } else {
                    vPaidModel.addPaidTiket(PaidTiketModel(p.foto, p.nama, p.harga, p.tanggal))
                }
            }
        }

        vPaidModel.listState.observe(this, Observer {
            val editor = container.edit()
            val listJson = Gson().toJson(vPaidModel.getListPaidTiket().value)
            editor.putString("paidtiket", listJson)
            editor.commit()
        })

        vModel.listState.observe(this, Observer {
            rvHistori.adapter = HistoriAdapter(vModel.getListHistori().value!!)
            rvHistori.layoutManager = LinearLayoutManager(this)

            val editor = container.edit()
            val listJson = Gson().toJson(vModel.getListHistori().value)
            editor.putString("histori", listJson)
            editor.commit()
        })
    }

    fun setMainButton(isConnected: Boolean) {
        if (isConnected) {
            supportFragmentManager.beginTransaction().apply {
                replace(R.id.frameHistori, fragButtonLogin)
                commit()
            }
        } else {
            supportFragmentManager.beginTransaction().apply {
                replace(R.id.frameHistori, fragButtonUnlogin)
                commit()
            }
        }
    }
}