package com.example.hawaiinaja

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.hawaiinaja.databinding.ActivityDaftarTiketBinding

class DaftarTiket : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityDaftarTiketBinding

    private lateinit var container: SharedPreferences
    val fragButtonUnlogin = MainButtonUnlogin()
    val fragButtonLogin = MainButtonLogin()

    private var idxTiket = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDaftarTiketBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bundle: Bundle? = intent.extras
        idxTiket = bundle?.get("idx") as Int

        val rvTiket = findViewById<RecyclerView>(R.id.daftarTiketRV)
        val viewModel = ViewModelProvider(this).get(TiketViewModel::class.java)

        when (idxTiket) {
            0 -> {
                viewModel.getListHW().observe(this, Observer {
                    rvTiket.adapter = TiketAdapter(viewModel.getListHW().value!!, this)
                    rvTiket.layoutManager = LinearLayoutManager(this)
                })
            }

            1 -> {
                viewModel.getListMNP().observe(this, Observer {
                    rvTiket.adapter = TiketAdapter(viewModel.getListMNP().value!!,this)
                    rvTiket.layoutManager = LinearLayoutManager(this)
                })
            }

            2 -> {
                viewModel.getListMSA().observe(this, Observer {
                    rvTiket.adapter = TiketAdapter(viewModel.getListMSA().value!!,this)
                    rvTiket.layoutManager = LinearLayoutManager(this)
                })
            }

            3 -> {
                viewModel.getListMG().observe(this, Observer {
                    rvTiket.adapter = TiketAdapter(viewModel.getListMG().value!!,this)
                    rvTiket.layoutManager = LinearLayoutManager(this)
                })
            }
        }

        binding.btnTiketKembali.setOnClickListener(this)

        container = getSharedPreferences("CONNECT", Context.MODE_PRIVATE)
        var connect = container.getBoolean("LOGIN", false)
        setMainButton(connect)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btnTiketKembali -> {
                intent = Intent(this, DaftarWahana::class.java)
                startActivity(intent)
            }
        }
    }

    private fun setMainButton(isConnected: Boolean) {
        if (isConnected) {
            supportFragmentManager.beginTransaction().apply {
                replace(R.id.frameDaftarTiket, fragButtonLogin)
                commit()
            }
        } else {
            supportFragmentManager.beginTransaction().apply {
                replace(R.id.frameDaftarTiket, fragButtonUnlogin)
                commit()
            }
        }
    }
}