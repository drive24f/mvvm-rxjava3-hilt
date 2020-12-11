package gits.movic.apptest.ui.dashboad

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import gits.movic.apptest.db.AppDataModel
import gits.movic.apptest.db.RealmHelper
import gits.movic.apptest.repository.Repository
import io.realm.RealmResults

class DashboardViewModel @ViewModelInject constructor(val repo: Repository, val helper: RealmHelper): ViewModel() {

    private var dataModel: MutableLiveData<RealmResults<AppDataModel>> = MutableLiveData()
    private var searchModel: MutableLiveData<RealmResults<AppDataModel>> = MutableLiveData()

    fun getObserveDataModel(): MutableLiveData<RealmResults<AppDataModel>> {
        return dataModel
    }

    fun getObserveSearchModel(): MutableLiveData<RealmResults<AppDataModel>> {
        return searchModel
    }

    fun removeById(id: Int) {
        helper.remove(id)
    }

    fun searchBy(key: String) {
        if(key.isEmpty()) {
            dataModel.value = helper.getAllData()
        } else {
            searchModel.value = helper.searchBy(key)
        }
    }
}