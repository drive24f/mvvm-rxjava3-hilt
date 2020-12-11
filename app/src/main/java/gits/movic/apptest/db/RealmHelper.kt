package gits.movic.apptest.db

import io.realm.Realm
import io.realm.RealmResults
import javax.inject.Inject

class RealmHelper @Inject constructor(val realm: Realm) {

    companion object {
        const val ID: String = "id"
        const val NAME: String = "name"
        const val PRODUCT_NAME: String = "productName"
    }

    private fun getNextKey(): Int {
        return if (realm.where(AppDataModel::class.java).count() > 0)
            realm.where(AppDataModel::class.java).max(ID)!!.toInt() + 1 else 0
    }

    fun addData(model: DataModel) {
        realm.beginTransaction()
        val data = realm.createObject(AppDataModel::class.java, getNextKey())
        data.name = model.name
        data.dob = model.dob
        data.lastUpdate = model.lastUpdate
        data.productName = model.productName
        data.accountType = model.accountType
        data.accountDate = model.accountDate
        data.starTime = model.starTime
        data.endTime = model.endTime
        realm.commitTransaction()
    }

    fun update(model: DataModel) {
        val data = AppDataModel()
        data.id = model.id
        data.name = model.name
        data.dob = model.dob
        data.lastUpdate = model.lastUpdate
        data.productName = model.productName
        data.accountType = model.accountType
        data.accountDate = model.accountDate
        data.starTime = model.starTime
        data.endTime = model.endTime
        realm.beginTransaction()
        realm.copyToRealmOrUpdate(data)
        realm.commitTransaction()
    }

    fun remove(id: Int) {
        val model: RealmResults<AppDataModel> =
            realm.where(AppDataModel::class.java)
                .equalTo(ID, id)
                .findAll()

        realm.executeTransaction { model.deleteFromRealm(0) }
        realm.refresh()
    }

    fun searchBy(name: String): RealmResults<AppDataModel> {
        return realm.where(AppDataModel::class.java)
            .like(NAME, name)
            .findAll()
    }

    fun getAllData(): RealmResults<AppDataModel> {
        return realm.where(AppDataModel::class.java).findAll()
    }
}