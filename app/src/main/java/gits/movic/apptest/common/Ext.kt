package gits.movic.apptest.common

import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

fun AppCompatActivity.setupToolbar(
    toolbar: Toolbar,
    titleTextView: TextView? = null,
    titleString: String,
    backClickHandler: (() -> Unit)? = null) {
    setSupportActionBar(toolbar)
    supportActionBar?.setDisplayHomeAsUpEnabled(true)
    supportActionBar?.setDisplayShowHomeEnabled(true)
    /*supportActionBar?.title = titleString*/
    titleTextView?.text = titleString
    toolbar.setNavigationOnClickListener { backClickHandler?.run { this() } ?: finish() }
}