package com.semba.weatherlogger.di.components

import android.app.Application
import com.semba.weatherlogger.base.BaseApplication
import com.semba.weatherlogger.di.modules.*
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import javax.inject.Singleton

/**
 * Created by SeMbA on 2020-01-02.
 */
@Singleton
@Component(modules = [
    AndroidInjectionModule::class,
    ActivityBindingModule::class,
    ViewModelModule::class,
    UtilsModule::class,
    DatabaseModule::class,
    NetworkModule::class,
    DataModule::class
])
interface AppComponent : AndroidInjector<BaseApplication> {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }
}
