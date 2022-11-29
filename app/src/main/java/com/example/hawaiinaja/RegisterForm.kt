package com.example.hawaiinaja

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.hawaiinaja.databinding.ActivityRegisterFormBinding

class RegisterForm : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityRegisterFormBinding

    private lateinit var container: SharedPreferences
    val fragButtonUnlogin = MainButtonUnlogin()
    val fragButtonLogin = MainButtonLogin()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterFormBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnRegis.setOnClickListener(this)

        container = getSharedPreferences("CONNECT", Context.MODE_PRIVATE)
        var connect = container.getBoolean("LOGIN", false)
        setMainButton(connect)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btnRegis -> {
                val editor = container.edit()

                val id = (binding.editRegUsername.text.toString()).toLowerCase()
                val pw = binding.editRegPassword.text.toString()
                val cpw = binding.editRegConfirmPassword.text.toString()

                if (pw == cpw) {
                    editor.putString("ID", id)
                    editor.putString("PW", pw)
                    editor.commit()

                    intent = Intent(this, LoginForm::class.java)
                    startActivity(intent)
                } else {
                    showMessage("Konfirmasi password salah!")
                }
            }
        }
    }

    private fun showMessage(s: String) {
        Toast.makeText(applicationContext, s, Toast.LENGTH_SHORT).show()
    }

    fun setMainButton(isConnected: Boolean) {
        if (isConnected) {
            supportFragmentManager.beginTransaction().apply {
                replace(R.id.frameRegister, fragButtonLogin)
                commit()
            }
        } else {
            supportFragmentManager.beginTransaction().apply {
                replace(R.id.frameRegister, fragButtonUnlogin)
                commit()
            }
        }
    }
}
