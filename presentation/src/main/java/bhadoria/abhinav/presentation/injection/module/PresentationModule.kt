package bhadoria.abhinav.presentation.injection.module

import android.content.Context
import android.net.ConnectivityManager
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import bhadoria.abhinav.presentation.injection.ViewModelFactory
import bhadoria.abhinav.presentation.ui.pullrequestviewer.PullRequestViewerActivity
import bhadoria.abhinav.presentation.ui.pullrequestviewer.PullRequestViewerViewModel
import bhadoria.abhinav.presentation.ui.splash.SplashActivity
import bhadoria.abhinav.presentation.ui.splash.SplashViewModel
import dagger.Binds
import dagger.MapKey
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap
import javax.inject.Singleton
import kotlin.reflect.KClass

@Module
abstract class PresentationModule {
    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(SplashViewModel::class)
    abstract fun bindSplashViewModel(viewModel: SplashViewModel): ViewModel

    @ContributesAndroidInjector
    abstract fun contributesSplashActivity(): SplashActivity

    @Binds
    @IntoMap
    @ViewModelKey(PullRequestViewerViewModel::class)
    abstract fun bindPullRequestViewModel(viewModel: PullRequestViewerViewModel): ViewModel

    @ContributesAndroidInjector
    abstract fun contributesPullRequestViewerActivity(): PullRequestViewerActivity

    @Module
    companion object {

        @Provides
        @Singleton
        @JvmStatic
        fun providesConnectivityManager(context: Context): ConnectivityManager =
                context.applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    }
}

@MustBeDocumented
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
@MapKey
annotation class ViewModelKey(val value: KClass<out ViewModel>)
