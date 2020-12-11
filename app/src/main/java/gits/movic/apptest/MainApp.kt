package gits.movic.apptest

import androidx.multidex.MultiDexApplication
import dagger.hilt.android.HiltAndroidApp
import io.realm.Realm
import io.realm.RealmConfiguration
import java.lang.Boolean.TRUE

@HiltAndroidApp
class MainApp: MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()

        Realm.init(this)
        Realm.setDefaultConfiguration(
            RealmConfiguration.Builder()
                .name(Realm.DEFAULT_REALM_NAME)
                .schemaVersion(1)
                .allowWritesOnUiThread(TRUE)
                .build()
        )
    }
}