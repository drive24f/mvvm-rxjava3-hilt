package gits.movic.apptest.ui.splash

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import dagger.hilt.android.AndroidEntryPoint
import gits.movic.apptest.BaseActivity
import gits.movic.apptest.databinding.ActivitySplashBinding
import gits.movic.apptest.ui.dashboad.DashboardActivity

@AndroidEntryPoint
class SplashActivity: BaseActivity<ActivitySplashBinding>() {

    private lateinit var viewModel : SplashViewModel

    override fun getActivityBinding(layoutInflater: LayoutInflater) = ActivitySplashBinding.inflate(
        layoutInflater
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[SplashViewModel::class.java]

        viewModel.startTimer()
        observeData()
    }

    private fun observeData() {
        viewModel.getTimeout().observe(this, {
            if (it) {
                DashboardActivity.start(context = this)
                this.finish()
            }
        })
    }
}