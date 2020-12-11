package gits.movic.apptest.ui.adddata

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import gits.movic.apptest.db.DataModel
import gits.movic.apptest.db.RealmHelper
import gits.movic.apptest.repository.Repository

class AddDataViewModel @ViewModelInject constructor(val repo: Repository, val helper: RealmHelper): ViewModel() {

    fun addData(model: DataModel) {
        helper.addData(model)
    }

    fun update(model: DataModel) {
        helper.update(model)
    }

    fun spinner(): MutableList<String> {
        val categories: MutableList<String> = ArrayList()
        categories.add("pilih")
        categories.add("Automobile")
        categories.add("Business Services")
        categories.add("Computers")
        categories.add("Education")
        categories.add("Personal")
        categories.add("Travel")
        return categories
    }
}
