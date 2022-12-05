package com.example.hawaiinaja

import android.content.SharedPreferences
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class HistoriAdapter(var hist: MutableList<HistoriModel>) :
    RecyclerView.Adapter<HistoriAdapter.HistoriViewHolder>() {
    inner class HistoriViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoriViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.histori, parent, false)
        return HistoriViewHolder(view)
    }

    override fun onBindViewHolder(holder: HistoriViewHolder, pos: Int) {
        holder.itemView.apply {
            findViewById<ImageView>(R.id.imgHTM).setImageResource(hist[pos].foto)
            findViewById<TextView>(R.id.textHTMNama).text = hist[pos].nama
            findViewById<TextView>(R.id.textHTMHarga).text = hist[pos].harga
            findViewById<TextView>(R.id.textHTMTanggal).text = hist[pos].tanggal
        }
    }

    override fun getItemCount(): Int {
        return hist.size
    }
}
