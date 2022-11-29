package com.example.hawaiinaja

import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStore
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
    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        holder.itemView.apply {
            findViewById<ImageView>(R.id.imgTM).setImageResource(tiket[position].foto)
            findViewById<TextView>(R.id.textTMNama).text = tiket[position].nama
            findViewById<TextView>(R.id.textTMHarga).text = tiket[position].harga
            findViewById<TextView>(R.id.textTMDesc).text = tiket[position].desc
            findViewById<Button>(R.id.btnTNVBeli).setOnClickListener {
                val date = findViewById<EditText>(R.id.editTMDate).text.toString()
                val isDateValid = checkDate(holder, date)
                val jumlah = findViewById<EditText>(R.id.editTMJumlah).text.toString().toInt()

                if (isDateValid && jumlah > 0)
                    showMessage(holder, "Tiket ditambahkan")
                else if (isDateValid && jumlah <= 0)
                    showMessage(holder, "Harap masukkan jumlah tiket")
                else if (!isDateValid)
                    showMessage(holder, "Tanggal tidak valid")

                container = context.getSharedPreferences("CONNECT", Context.MODE_PRIVATE)
                if (isDateValid) {
                    val viewModel =
                        ViewModelProvider(lifecycleOwner).get(UnpaidTiketViewModel::class.java)

                    val serializedUser = container.getString("unpaidtiket", null)

                    if(!serializedUser.isNullOrEmpty()) {
                        val type = object : TypeToken<MutableList<UnpaidTiketModel>>() {}.type
                        val listUnpaidTiket =
                            Gson().fromJson<MutableList<UnpaidTiketModel>>(serializedUser, type)

                        listUnpaidTiket.forEach { un ->
                            viewModel.addUnpaidTiket(
                                UnpaidTiketModel(un.foto, un.nama, un.harga, un.tanggal, un.isChanged)
                            )
                        }
                    }

                    if (jumlah > 0) {
                        for (i in 1..jumlah) {
                            viewModel.addUnpaidTiket(
                                UnpaidTiketModel(
                                    tiket[position].foto,
                                    tiket[position].nama,
                                    tiket[position].harga,
                                    date,
                                    false
                                )
                            )
                        }
                    }

                    val editor = container.edit()
                    val listJson = Gson().toJson(viewModel.getListUnpaidTiket().value)
                    editor.putString("unpaidtiket", listJson)
                    editor.commit()
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return tiket.size
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun checkDate(holder: TodoViewHolder, date: String): Boolean {
        val validFormat = date.count { c -> c == '/' } == 2

        if (validFormat) {
            val validDate = date.split("/").toTypedArray()
            val totalDay = LocalDate.of(
                validDate[2].toInt(),
                validDate[1].toInt(),
                validDate[0].toInt()
            ).dayOfYear

            if (validDate[2].toInt() >= LocalDate.now().year) {
                if (LocalDate.now().dayOfYear + 1 <= totalDay) {
                    return true
                } else {
                    return false
                }
            } else {
                return false
            }
        } else {
            return false
        }
    }

    fun showMessage(holder: TodoViewHolder, s: String) {
        holder.itemView.apply {
            Toast.makeText(context, s, Toast.LENGTH_SHORT).show()
        }
    }
}
