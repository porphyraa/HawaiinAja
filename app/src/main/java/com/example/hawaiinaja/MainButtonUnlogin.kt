package com.example.hawaiinaja

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.hawaiinaja.R.*
import kotlinx.android.synthetic.main.fragment_main_button_unlogin.view.*


class MainButtonUnlogin : Fragment() {

    private lateinit var container: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(layout.fragment_main_button_unlogin, container, false)

        view.btnMainUn1.setOnClickListener {
            val intent = Intent(requireContext(), LoginForm::class.java)
            startActivity(intent)
        }

        view.btnMainUn2.setOnClickListener {
            val intent = Intent(requireContext(), RegisterForm::class.java)
            startActivity(intent)
        }

        view.btnMainUn3.setOnClickListener {
            val intent = Intent(requireContext(), DaftarWahana::class.java)
            startActivity(intent)
        }

        return view
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            MainButtonUnlogin().apply {
                arguments = Bundle().apply {
                }
            }
    }
}