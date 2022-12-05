package com.example.hawaiinaja

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.hawaiinaja.databinding.ActivityPembayaranBinding
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Pembayaran : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityPembayaranBinding

    private lateinit var container: SharedPreferences
    private val fragButtonUnlogin = MainButtonUnlogin()
    private val fragButtonLogin = MainButtonLogin()

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

        if (!serializedUser.isNullOrEmpty()) {
            listUnpaidTiket.forEach { un ->
                vModel.addUnpaidTiket(
                    UnpaidTiketModel(un.foto, un.nama, un.harga, un.tanggal)
                )
            }
        }

        vModel.listState.observe(this, Observer {
            rvUnpaidTiket.adapter = UnpaidTiketAdapter(vModel.getListUnpaidTiket().value!!, this)
            rvUnpaidTiket.layoutManager = LinearLayoutManager(this)

            val totalPrice =
                if (vModel.getListUnpaidTiket().value!!.size == 0) 0 else countTotalPrice(vModel.getListUnpaidTiket().value!!)
            binding.textPembayaranTotal.text = "IDR $totalPrice"

            binding.btnPembayaranBayar.isEnabled =
                !vModel.getListUnpaidTiket().value.isNullOrEmpty()
        })

        binding.btnPembayaranBayar.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btnPembayaranBayar -> {
                val vModelPaid = ViewModelProvider(this).get(PaidTiketViewModel::class.java)
                val vModelUnpaid = ViewModelProvider(this).get(UnpaidTiketViewModel::class.java)

                val serializedPaidTiket = container.getString("paidtiket", null)
                val typePaid = object : TypeToken<MutableList<PaidTiketModel>>() {}.type
                val listPaidTiket =
                    Gson().fromJson<MutableList<PaidTiketModel>>(serializedPaidTiket, typePaid)

                val serializedUnpaidTiket = container.getString("unpaidtiket", null)
                val type = object : TypeToken<MutableList<UnpaidTiketModel>>() {}.type
                val listUnpaidTiket =
                    Gson().fromJson<MutableList<UnpaidTiketModel>>(serializedUnpaidTiket, type)

                if (!serializedPaidTiket.isNullOrEmpty()) {
                    listPaidTiket.forEach { p ->
                        vModelPaid.addPaidTiket(
                            PaidTiketModel(p.foto, p.nama, p.harga, p.tanggal)
                        )
                    }
                }

                if (!serializedUnpaidTiket.isNullOrEmpty()) {
                    listUnpaidTiket.forEach { un ->
                        vModelPaid.addPaidTiket(
                            PaidTiketModel(un.foto, un.nama, un.harga, un.tanggal)
                        )
                    }
                }

                vModelUnpaid.removeAllUnpaidTiket()

                val editor = container.edit()
                val listJson = Gson().toJson(vModelPaid.getListPaidTiket().value)
                editor.putString("paidtiket", listJson)
                editor.remove("unpaidtiket")
                editor.commit()

                showMessage("Pembayaran berhasil!")
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

    fun showMessage(s: String) {
        Toast.makeText(applicationContext, s, Toast.LENGTH_SHORT).show()
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

}

