package com.example.hawaiinaja

import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.time.LocalDate

class TiketAdapter(var tiket: MutableList<TiketModel>, val lifecycleOwner: ViewModelStoreOwner) :
    RecyclerView.Adapter<TiketAdapter.TodoViewHolder>() {
    inner class TodoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    private lateinit var container: SharedPreferences

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.tiket, parent, false)
        return TodoViewHolder(view)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: TodoViewHolder, pos: Int) {
        holder.itemView.apply {
            findViewById<ImageView>(R.id.imgTM).setImageResource(tiket[pos].foto)
            findViewById<TextView>(R.id.textTMNama).text = tiket[pos].nama
            findViewById<TextView>(R.id.textTMHarga).text = tiket[pos].harga
            findViewById<TextView>(R.id.textTMDesc).text = tiket[pos].desc
            findViewById<Button>(R.id.btnTNVBeli).setOnClickListener {
                val date = findViewById<EditText>(R.id.editTMDate).text.toString()
                if (date == "") {
                    showMessage(holder, "Masukkan tanggal kedatangan terlebih dahulu!")
                } else {
                    val isDateValid = checkDate(date)
                    val jumlah = findViewById<EditText>(R.id.editTMJumlah).text.toString().toInt()

                    container = context.getSharedPreferences("CONNECT", Context.MODE_PRIVATE)
                    if (isDateValid && jumlah > 0) {
                        showMessage(holder, "Tiket berhasil ditambahkan")

                        val viewModel =
                            ViewModelProvider(lifecycleOwner).get(UnpaidTiketViewModel::class.java)

                        val serializedUser = container.getString("unpaidtiket", null)

                        if (!serializedUser.isNullOrEmpty()) {
                            val type = object : TypeToken<MutableList<UnpaidTiketModel>>() {}.type
                            val listUnpaidTiket =
                                Gson().fromJson<MutableList<UnpaidTiketModel>>(serializedUser, type)

                            listUnpaidTiket.forEach { un ->
                                viewModel.addUnpaidTiket(
                                    UnpaidTiketModel(un.foto, un.nama, un.harga, un.tanggal)
                                )
                            }
                        }

                        for (i in 1..jumlah) {
                            viewModel.addUnpaidTiket(
                                UnpaidTiketModel(
                                    tiket[pos].foto, tiket[pos].nama, tiket[pos].harga, date
                                )
                            )
                        }

                        val editor = container.edit()
                        val listJson = Gson().toJson(viewModel.getListUnpaidTiket().value)
                        editor.putString("unpaidtiket", listJson)
                        editor.commit()
                    } else if (isDateValid && jumlah <= 0) {
                        showMessage(holder, "Harap masukkan jumlah tiket!")
                    } else if (!isDateValid) {
                        showMessage(holder, "Tanggal tidak valid!")
                    }
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return tiket.size
    }

    companion object {
        @RequiresApi(Build.VERSION_CODES.O)
        fun checkDate(date: String): Boolean {
            val validFormat = date.count { c -> c == '/' } == 2

            if (validFormat) {
                val validDate = date.split("/").toTypedArray()
                val day = validDate[0].toInt()
                val month = validDate[1].toInt()
                val year = validDate[2].toInt()
                val now = LocalDate.now()
                if (day <= now.lengthOfMonth() && month <= 12 && year >= now.year) {
                    val totalDay = LocalDate.of(year, month, day).dayOfYear
                    return now.dayOfYear + 1 <= totalDay
                } else {
                    return false
                }
            } else {
                return false
            }
        }
    }

    private fun showMessage(holder: TodoViewHolder, s: String) {
        holder.itemView.apply {
            Toast.makeText(context, s, Toast.LENGTH_SHORT).show()
        }
    }
}
