package gits.movic.apptest.ui.adddata

import android.R
import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.VISIBLE
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import dagger.hilt.android.AndroidEntryPoint
import gits.movic.apptest.BaseActivity
import gits.movic.apptest.common.setupToolbar
import gits.movic.apptest.databinding.ActivityAddDataBinding
import gits.movic.apptest.db.DataModel
import java.util.*

@AndroidEntryPoint
class AddDataActivity: BaseActivity<ActivityAddDataBinding>() {

    private lateinit var viewModel : AddDataViewModel

    private var calendarObserve: MutableLiveData<String> = MutableLiveData()
    private var inputName: MutableLiveData<String> = MutableLiveData()
    private var productSelection: MutableLiveData<String> = MutableLiveData()

    override fun getActivityBinding(layoutInflater: LayoutInflater) = ActivityAddDataBinding.inflate(
        layoutInflater
    )

    companion object{
        fun start(context: Context) {
            context.startActivity(Intent(context, AddDataActivity::class.java))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[AddDataViewModel::class.java]
        setupToolbar(bind.toolbar, bind.title, "Create/Update Data")

        initButton()
        observeData()
        showSpinner()
    }

    private fun initButton() {
        bind.btnCalendar.setOnClickListener {
            val calendar = Calendar.getInstance()
            val year = calendar[Calendar.YEAR]
            val month = calendar[Calendar.MONTH]
            val day = calendar[Calendar.DAY_OF_MONTH]

            val datePickerDialog = DatePickerDialog(
                this, { _, y, m, d ->
                    val dob = "$d/${(m + 1)}/$y"
                    calendarObserve.value = dob
                }, year, month, day
            )
            datePickerDialog.show()
        }

        bind.btnSave.setOnClickListener {
            viewModel.addData(
                DataModel(
                    name = bind.inputName.text.toString(),
                    dob = bind.textCalendar.text.toString(),
                    lastUpdate = "Sun, 23 Febuary, 2020",
                    productName = productSelection.value!!
                )
            )
            finish()
        }

        bind.spinner.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelected(v: AdapterView<*>?, item: View, position: Int, id: Long) {
                if(position != 0) {
                    productSelection.value = viewModel.spinner()[position]
                }
            }

            override fun onNothingSelected(parentView: AdapterView<*>?) {

            }
        }
    }

    private fun showSpinner() {
        val dataAdapter: ArrayAdapter<String> =
            ArrayAdapter<String>(this, R.layout.simple_spinner_item, viewModel.spinner())
        dataAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item)
        bind.spinner.adapter = dataAdapter
    }

    private fun observeData() {
        inputName.observe(this, { m ->

        })

        calendarObserve.observe(this, { m ->
            bind.textCalendar.text = m
            bind.layoutProductSelection.visibility = VISIBLE
        })

        productSelection.observe(this, { m ->
            bind.layoutProductInfo.visibility = VISIBLE
        })
    }
}