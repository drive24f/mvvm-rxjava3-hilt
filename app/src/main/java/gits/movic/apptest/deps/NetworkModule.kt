package gits.movic.apptest.deps

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import gits.movic.apptest.db.RealmHelper
import gits.movic.apptest.network.ApiService
import gits.movic.apptest.repository.Repository
import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory
import io.realm.Realm
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
class NetworkModule {

    @Provides
    @Singleton
    fun provideApiService(): ApiService {
        return Retrofit.Builder()
            .baseUrl("https://pokeapi.co/api/v2/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .build()
            .create(ApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideGson(): Gson = GsonBuilder().create()

    @Provides
    @Singleton
    fun provideRepository(api: ApiService) = Repository(api)
}