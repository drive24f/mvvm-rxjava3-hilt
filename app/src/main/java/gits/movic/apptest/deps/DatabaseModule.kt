package gits.movic.apptest.deps

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import gits.movic.apptest.db.RealmHelper
import io.realm.Realm
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
class DatabaseModule {

    @Provides
    @Singleton
    fun provideRealm(): Realm = Realm.getDefaultInstance()

    @Provides
    @Singleton
    fun provideRealmHelper(realm: Realm) = RealmHelper(realm)
}