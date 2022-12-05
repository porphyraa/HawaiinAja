package com.example.hawaiinaja

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_main_button_login.view.*
import kotlinx.android.synthetic.main.fragment_main_button_unlogin.view.*

class MainButtonLogin : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_main_button_login, container, false)
        view.btnMainLog1.setOnClickListener {
            val intent = Intent(requireContext(), DaftarWahana::class.java)
            startActivity(intent)
        }

        view.btnMainLog2.setOnClickListener {
            val intent = Intent(requireContext(), Pembayaran::class.java)
            startActivity(intent)
        }

        view.btnMainLog3.setOnClickListener {
            val intent = Intent(requireContext(), TiketSaya::class.java)
            startActivity(intent)
        }

        view.btnMainLog4.setOnClickListener {
            val intent = Intent(requireContext(), Histori::class.java)
            startActivity(intent)
        }

        view.btnMainLog5.setOnClickListener {
            val editor = this.activity!!
                .getSharedPreferences("CONNECT", Context.MODE_PRIVATE).edit()
            editor.putBoolean("LOGIN", false)
            editor.commit()

            val intent = Intent(requireContext(), MainActivity::class.java)
            startActivity(intent)
        }

        return view
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            MainButtonLogin().apply {
                arguments = Bundle().apply {
                }
            }
    }
}