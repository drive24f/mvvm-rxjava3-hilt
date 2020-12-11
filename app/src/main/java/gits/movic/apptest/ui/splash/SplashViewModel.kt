package gits.movic.apptest.ui.splash

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import gits.movic.apptest.repository.Repository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import java.util.concurrent.TimeUnit

class SplashViewModel @ViewModelInject constructor(val repo: Repository): ViewModel() {

    private var isTimeout = MutableLiveData<Boolean>()

    fun getTimeout(): MutableLiveData<Boolean> {
        return isTimeout
    }

    fun startTimer() {
        Observable.range(0, 2)
            .flatMap { v -> Observable.just(v).delay((2 - v).toLong(), TimeUnit.SECONDS) }
            .observeOn(AndroidSchedulers.mainThread())
            .doOnTerminate { isTimeout.value = true }
            .subscribe()
    }
}