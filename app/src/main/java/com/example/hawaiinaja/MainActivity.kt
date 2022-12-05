package com.example.hawaiinaja

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.hawaiinaja.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityMainBinding

    private lateinit var container: SharedPreferences
    private val fragButtonUnlogin = MainButtonUnlogin()
    private val fragButtonLogin = MainButtonLogin()
    private var connect: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnPesanTiket.setOnClickListener(this)

        container = getSharedPreferences("CONNECT", Context.MODE_PRIVATE)
        connect = container.getBoolean("LOGIN", false)
        setMainButton(connect)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btnPesanTiket -> {
                if (connect)
                    intent = Intent(this, DaftarWahana::class.java)
                else
                    intent = Intent(this, LoginForm::class.java)
                startActivity(intent)
            }
        }
    }

    private fun setMainButton(isConnected: Boolean) {
        if (isConnected) {
            supportFragmentManager.beginTransaction().apply {
                replace(R.id.frameMain, fragButtonLogin)
                commit()
            }
        } else {
            supportFragmentManager.beginTransaction().apply {
                replace(R.id.frameMain, fragButtonUnlogin)
                commit()
            }
        }
    }
}