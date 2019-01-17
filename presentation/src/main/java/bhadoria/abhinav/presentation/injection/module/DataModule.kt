package bhadoria.abhinav.presentation.injection.module

import android.app.Application
import android.content.Context
import bhadoria.abhinav.data.factory.GithubRepositoryFactory
import bhadoria.abhinav.data.local.room.MeeshoRoomDatabase
import bhadoria.abhinav.data.local.sharedpref.IPreferenceStorage
import bhadoria.abhinav.data.local.sharedpref.SharedIPreferenceStorage
import bhadoria.abhinav.data.remote.retrofit.factory.GithubServiceFactory
import bhadoria.abhinav.data.remote.retrofit.service.GithubService
import bhadoria.abhinav.domain.repository.factory.IGithubRepositoryFactory
import bhadoria.abhinav.presentation.BuildConfig
import com.google.gson.Gson
import com.readystatesoftware.chuck.ChuckInterceptor
import dagger.Binds
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
abstract class DataModule {

    @Module
    companion object {

        // JvsStatic provides static method
        @Provides
        @Singleton
        @JvmStatic
        fun provideGithubService(chuckInterceptor: ChuckInterceptor): GithubService {
            return GithubServiceFactory.makeRetrofitClient(BuildConfig.DEBUG, chuckInterceptor)
        }

        @Provides
        @JvmStatic
        fun provideChuckInterceptor(context: Context): ChuckInterceptor = ChuckInterceptor(context)

        @Provides
        @Singleton
        @JvmStatic
        fun providesDataBase(application: Application): MeeshoRoomDatabase {
            return MeeshoRoomDatabase.getInstance(application)
        }

        @Provides
        @Singleton
        @JvmStatic
        fun providesGson(): Gson = Gson()
    }

    @Binds
    abstract fun bindPreferenceStorage(sharedPreferenceStorage: SharedIPreferenceStorage): IPreferenceStorage

    @Binds
    abstract fun bindRepositoryFactory(repositoryFactory: GithubRepositoryFactory): IGithubRepositoryFactory
}