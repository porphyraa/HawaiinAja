package com.example.hawaiinaja

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.hawaiinaja.databinding.ActivityPembayaranBinding
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Pembayaran : AppCompatActivity() {

    private lateinit var binding: ActivityPembayaranBinding

    private lateinit var container: SharedPreferences
    val fragButtonUnlogin = MainButtonUnlogin()
    val fragButtonLogin = MainButtonLogin()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPembayaranBinding.inflate(layoutInflater)
        setContentView(binding.root)

        container = getSharedPreferences("CONNECT", Context.MODE_PRIVATE)
        var connect = container.getBoolean("LOGIN", false)
        setMainButton(connect)

        val rvUnpaidTiket = findViewById<RecyclerView>(R.id.daftarUnpaidTiketRV)
        val vModel = ViewModelProvider(this).get(UnpaidTiketViewModel::class.java)

        val serializedUser = container.getString("unpaidtiket", null)
        val type = object : TypeToken<MutableList<UnpaidTiketModel>>() {}.type
        val listUnpaidTiket = Gson().fromJson<MutableList<UnpaidTiketModel>>(serializedUser, type)

        listUnpaidTiket.forEach { un ->
            vModel.addUnpaidTiket(
                UnpaidTiketModel(
                    un.foto,
                    un.nama,
                    un.harga,
                    un.tanggal,
                    un.isChanged
                )
            )
        }

        vModel.listState.observe(this, Observer {
            rvUnpaidTiket.adapter = UnpaidTiketAdapter(vModel.getListUnpaidTiket().value!!, this)
            rvUnpaidTiket.layoutManager = LinearLayoutManager(this)

            val totalPrice = if(vModel.getListUnpaidTiket().value!!.size==0) 0 else countTotalPrice(vModel.getListUnpaidTiket().value!!)
            binding.textPembayaranTotal.text = "IDR $totalPrice"
            Log.d("abc", totalPrice.toString())
        })
    }

    private fun setMainButton(isConnected: Boolean) {
        if (isConnected) {
            supportFragmentManager.beginTransaction().apply {
                replace(R.id.framePembayaran, fragButtonLogin)
                commit()
            }
        } else {
            supportFragmentManager.beginTransaction().apply {
                replace(R.id.framePembayaran, fragButtonUnlogin)
                commit()
            }
        }
    }

    private fun countTotalPrice(listUnpaidTiket: MutableList<UnpaidTiketModel>): Int {
        var totalPrice = 0

        listUnpaidTiket.forEach { un ->
            val harga = un.harga.substring(3).replace(" ", "").replace(".", "").toInt()
            totalPrice += harga
        }

        return totalPrice
    }
}

