package gits.movic.apptest.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import gits.movic.apptest.R
import gits.movic.apptest.databinding.ItemDataBinding
import gits.movic.apptest.db.AppDataModel
import java.lang.Boolean.FALSE
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class DashboardAdapter : RecyclerView.Adapter<DashboardAdapter.BindingHolder>() {

    private var models: MutableList<AppDataModel> = mutableListOf()

    private lateinit var listener: (Any, Int) -> Unit

    override fun getItemCount(): Int = models.size

    override fun onCreateViewHolder(parent: ViewGroup, type: Int): BindingHolder {
        val v: View = LayoutInflater.from(parent.context).inflate(
            R.layout.item_data, parent, FALSE
        )
        return BindingHolder(v)
    }

    override fun onBindViewHolder(holder: BindingHolder, position: Int) {
        val model: AppDataModel = models[position]
        holder.bindItem(model, listener, position)
    }

    fun showData(models: MutableList<AppDataModel>) {
        this.models = models
        notifyDataSetChanged()
    }

    fun getItem(listener: (Any, Int) -> Unit) {
        this.listener = listener
    }

    fun refreshAdapter() {
        notifyDataSetChanged()
    }

    fun isEmpty(): Boolean {
        return models.isEmpty()
    }

    class BindingHolder(private val rowView: View) : RecyclerView.ViewHolder(rowView) {

        private var bind: ItemDataBinding? = DataBindingUtil.bind(rowView)

        fun bindItem(model: AppDataModel, listener: (Any, Int) -> Unit, position: Int) {
            bind?.let {
                it.textName.text = model.name
                it.textDob.text = model.dob
                it.textpdateValue.text = model.lastUpdate
                it.textProductName.text = model.productName

                it.btnRemove.setOnClickListener { listener(model, position) }
            }
        }

        private fun getContext(): Context {
            return rowView.context
        }

        @SuppressLint("SimpleDateFormat")
        fun toFormatDate(dats: String): String {
            val input = SimpleDateFormat("yyyy-MM-dd HH:mm:ss.000000")
            val output = SimpleDateFormat("yyyy-MM-dd | HH:mm:ss")
            var d: Date? = null
            try {
                d = input.parse(dats)
            } catch (e: ParseException) {
                e.printStackTrace()
            }
            return output.format(d!!)
        }
    }
}