package bhadoria.abhinav.presentation.injection.component

import android.app.Application
import bhadoria.abhinav.presentation.MyApplication
import bhadoria.abhinav.presentation.injection.module.ApplicationModule
import bhadoria.abhinav.presentation.injection.module.DataModule
import bhadoria.abhinav.presentation.injection.module.PresentationModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(AndroidInjectionModule::class,
        ApplicationModule::class,
        PresentationModule::class,
        DataModule::class))
interface ApplicationComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): ApplicationComponent
    }

    fun inject(app: MyApplication)
}