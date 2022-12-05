package com.example.hawaiinaja

import android.content.SharedPreferences
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView

class PaidTiketAdapter(var ptiket: MutableList<PaidTiketModel>) :
    RecyclerView.Adapter<PaidTiketAdapter.PaidViewHolder>() {
    inner class PaidViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PaidViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.paidtiket, parent, false)
        return PaidViewHolder(view)
    }

    override fun onBindViewHolder(holder: PaidViewHolder, pos: Int) {
        holder.itemView.apply {
            findViewById<ImageView>(R.id.imgPTM).setImageResource(ptiket[pos].foto)
            findViewById<TextView>(R.id.textPTMNama).text = ptiket[pos].nama
            findViewById<TextView>(R.id.textPTMHarga).text = ptiket[pos].harga
            findViewById<TextView>(R.id.editPTMTanggal).text = ptiket[pos].tanggal
            findViewById<TextView>(R.id.editPTMTanggal).setOnFocusChangeListener { _, hasFocus ->
                if (hasFocus)
                    findViewById<Button>(R.id.btnPTMUbah).visibility = View.VISIBLE
                else
                    findViewById<Button>(R.id.btnPTMUbah).visibility = View.INVISIBLE
            }
        }
    }

    override fun getItemCount(): Int {
        return ptiket.size
    }
}
