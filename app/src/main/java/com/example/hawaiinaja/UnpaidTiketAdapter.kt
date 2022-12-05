package com.example.hawaiinaja

import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson

class UnpaidTiketAdapter(
    var utiket: MutableList<UnpaidTiketModel>,
    val lifecycleOwner: ViewModelStoreOwner
) :
    RecyclerView.Adapter<UnpaidTiketAdapter.UnpaidViewHolder>() {
    inner class UnpaidViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    private lateinit var container: SharedPreferences

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UnpaidViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.unpaidtiket, parent, false)
        return UnpaidViewHolder(view)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: UnpaidViewHolder, position: Int) {
        holder.itemView.apply {
            findViewById<ImageView>(R.id.imgUTM).setImageResource(utiket[position].foto)
            findViewById<TextView>(R.id.textUTMNama).text = utiket[position].nama
            findViewById<TextView>(R.id.textUTMHarga).text = utiket[position].harga
            findViewById<TextView>(R.id.textUTMTanggal).text = utiket[position].tanggal
            findViewById<ImageView>(R.id.imgUTMTrash).setOnClickListener {
                val viewModel =
                    ViewModelProvider(lifecycleOwner).get(UnpaidTiketViewModel::class.java)
                viewModel.removeUnpaidTiket(position)

                container = context.getSharedPreferences("CONNECT", Context.MODE_PRIVATE)
                val editor = container.edit()
                val listJson = Gson().toJson(viewModel.getListUnpaidTiket().value)
                editor.putString("unpaidtiket", listJson)
                editor.commit()
                showMessage(holder, "Tiket dihapus")
            }
        }
    }

    override fun getItemCount(): Int {
        return utiket.size
    }

    fun showMessage(holder: UnpaidViewHolder, s: String) {
        holder.itemView.apply { Toast.makeText(context, s, Toast.LENGTH_SHORT).show() }
    }
}
