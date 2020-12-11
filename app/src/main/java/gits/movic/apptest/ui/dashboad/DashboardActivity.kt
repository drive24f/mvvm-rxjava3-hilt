package gits.movic.apptest.ui.dashboad

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.jakewharton.rxbinding.widget.RxTextView
import dagger.hilt.android.AndroidEntryPoint
import gits.movic.apptest.BaseActivity
import gits.movic.apptest.adapters.DashboardAdapter
import gits.movic.apptest.common.LineDeviderDecoration
import gits.movic.apptest.common.setupToolbar
import gits.movic.apptest.databinding.ActivityDashboardBinding
import gits.movic.apptest.db.AppDataModel
import gits.movic.apptest.ui.adddata.AddDataActivity
import rx.Observable
import rx.android.schedulers.AndroidSchedulers
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class DashboardActivity: BaseActivity<ActivityDashboardBinding>() {

    private lateinit var viewModel : DashboardViewModel
    private lateinit var linearLayoutManager: LinearLayoutManager
    private var dashboarAdapter: DashboardAdapter = DashboardAdapter()

    override fun getActivityBinding(layoutInflater: LayoutInflater) = ActivityDashboardBinding.inflate(layoutInflater)

    companion object{
        fun start(context: Context) {
            context.startActivity(Intent(context, DashboardActivity::class.java))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider( this)[DashboardViewModel::class.java]
        setupToolbar(bind.toolbar, bind.title, "DASHBOARD")

        initButton()
        initAdapter()
        observeData()
        observeInput()
        viewModel.searchBy(key = "")
    }

    override fun onResume() {
        super.onResume()
        dashboarAdapter.refreshAdapter()
        if(dashboarAdapter.isEmpty()) {
            bind.textEmpty.visibility = VISIBLE
        } else {
            bind.textEmpty.visibility = GONE
        }
    }

    private fun initButton() {
        bind.btnAdd.setOnClickListener {
            AddDataActivity.start(context = this)
        }

        bind.btnSearch.setOnClickListener {
            viewModel.searchBy(key = bind.inputSearch.text.toString())
        }

        dashboarAdapter.getItem { item, _ ->
            if (item is AppDataModel) {
                viewModel.removeById(item.id)
                onResume()
            }
        }
    }

    private fun initAdapter() {
        linearLayoutManager = LinearLayoutManager(this)
        bind.recyclerView.apply {
            this.layoutManager = linearLayoutManager
            this.isNestedScrollingEnabled = false
            this.setHasFixedSize(true)
            this.addItemDecoration(LineDeviderDecoration(context = this@DashboardActivity))
            this.adapter = dashboarAdapter
        }
    }

    private fun observeInput() {
        val userNameObsesrvable: Observable<Boolean> = RxTextView
            .textChanges(bind.inputSearch)
            .debounce(100, TimeUnit.MILLISECONDS)
            .map { !TextUtils.isEmpty(it) }

        userNameObsesrvable.observeOn(AndroidSchedulers.mainThread())
            .map { if(!it) viewModel.searchBy(key = "") }
            .subscribe()
    }

    private fun observeData() {
        viewModel.getObserveDataModel().observe(this, { m ->
            if(m.size == 0) {
                bind.textEmpty.visibility = VISIBLE
            } else {
                bind.textEmpty.visibility = GONE
            }
            dashboarAdapter.showData(m)
        })

        viewModel.getObserveSearchModel().observe(this, { m ->
            if(m.size == 0) {
                bind.textEmpty.visibility = VISIBLE
            } else {
                bind.textEmpty.visibility = GONE
            }
            dashboarAdapter.showData(m)
        })
    }
}