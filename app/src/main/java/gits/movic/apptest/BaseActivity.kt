package gits.movic.apptest

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding

abstract class BaseActivity<vb: ViewBinding>: AppCompatActivity() {

    protected lateinit var bind: vb
    abstract fun getActivityBinding(layoutInflater: LayoutInflater): vb

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = getActivityBinding(layoutInflater)
        setContentView(bind.root)
    }
}