package gits.movic.apptest.db

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class AppDataModel(
    @PrimaryKey
    var id: Int = 0,
    var name: String = "",
    var dob: String = "",
    var lastUpdate: String = "",
    var productName: String = "",
    var accountType: String = "",
    var accountDate: String = "",
    var starTime: String = "",
    var endTime: String = ""
) : RealmObject()

data class DataModel(
    var id: Int = 0,
    var name: String = "",
    var dob: String = "",
    var lastUpdate: String = "",
    var productName: String = "",
    var accountType: String = "",
    var accountDate: String = "",
    var starTime: String = "",
    var endTime: String = ""
)