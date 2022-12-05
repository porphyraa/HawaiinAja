package com.example.hawaiinaja

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.hawaiinaja.databinding.ActivityDaftarWahanaBinding
import com.example.hawaiinaja.databinding.ActivityMainBinding

class DaftarWahana : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityDaftarWahanaBinding

    private lateinit var container: SharedPreferences
    private val fragButtonUnlogin = MainButtonUnlogin()
    private val fragButtonLogin = MainButtonLogin()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDaftarWahanaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnWahanaHw.setOnClickListener(this)
        binding.btnWahanaMnp.setOnClickListener(this)
        binding.btnWahanaMsa.setOnClickListener(this)
        binding.btnWahanaMg.setOnClickListener(this)
        binding.btnWahanaKembali.setOnClickListener(this)

        container = getSharedPreferences("CONNECT", Context.MODE_PRIVATE)
        var connect = container.getBoolean("LOGIN", false)
        setMainButton(connect)
    }

    override fun onClick(v: View?) {
        when (v?.id) {

            R.id.btnWahanaHw -> {
                intent = Intent(this, DaftarTiket::class.java)
                intent.putExtra("idx", 0)
                startActivity(intent)
            }

            R.id.btnWahanaMnp -> {
                intent = Intent(this, DaftarTiket::class.java)
                intent.putExtra("idx", 1)
                startActivity(intent)
            }

            R.id.btnWahanaMsa -> {
                intent = Intent(this, DaftarTiket::class.java)
                intent.putExtra("idx", 2)
                startActivity(intent)
            }

            R.id.btnWahanaMg -> {
                intent = Intent(this, DaftarTiket::class.java)
                intent.putExtra("idx", 3)
                startActivity(intent)
            }

            R.id.btnWahanaKembali -> {
                intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }


        }
    }

    fun setMainButton(isConnected: Boolean) {
        if (isConnected) {
            supportFragmentManager.beginTransaction().apply {
                replace(R.id.frameDaftarWahana, fragButtonLogin)
                commit()
            }
        } else {
            supportFragmentManager.beginTransaction().apply {
                replace(R.id.frameDaftarWahana, fragButtonUnlogin)
                commit()
            }
        }
    }
}