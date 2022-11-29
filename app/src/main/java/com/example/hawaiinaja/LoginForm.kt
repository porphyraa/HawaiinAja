package com.example.hawaiinaja

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.hawaiinaja.databinding.ActivityLoginFormBinding

class LoginForm : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityLoginFormBinding

    private lateinit var container: SharedPreferences
    val fragButtonUnlogin = MainButtonUnlogin()
    val fragButtonLogin = MainButtonLogin()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginFormBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnLogin.setOnClickListener(this)

        container = getSharedPreferences("CONNECT", Context.MODE_PRIVATE)
        var connect = container.getBoolean("LOGIN", false)
        setMainButton(connect)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.textLoginRegister -> {
//                intent = Intent(this, RegisterForm::class.java)
//                startActivity(intent)
                var conNow = container.getBoolean("LOGIN", false)
                setMainButton(!conNow)
            }

            R.id.btnLogin -> {
                val id = container.getString("ID", "")
                val pw = container.getString("PW", "")

                val inputId = (binding.editLoginUsername.text.toString()).toLowerCase()
                val inputPw = binding.editLoginPassword.text.toString()

                if (inputId != "") {
                    if (id == inputId && pw == inputPw) {
                        val editor = container.edit()
                        editor.putBoolean("LOGIN", true)
                        editor.commit()

                        intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                    } else {
                        showMessage("Username atau password salah")
                    }
                } else {
                    showMessage("Username atau password salah")
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
                replace(R.id.frameLogin, fragButtonLogin)
                commit()
            }
        } else {
            supportFragmentManager.beginTransaction().apply {
                replace(R.id.frameLogin, fragButtonUnlogin)
                commit()
            }
        }
    }

}