package com.semba.weatherlogger.di.modules

import com.semba.weatherlogger.ui.detailsScreen.DetailsFragment
import com.semba.weatherlogger.ui.homeScreen.HomeFragment
import com.semba.weatherlogger.ui.mainNavScreen.MainNavActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Created by SeMbA on 2020-01-02.
 */
@Module
abstract class ActivityBindingModule {

    @ContributesAndroidInjector(modules = [ViewModelModule::class])
    abstract fun contributeMainNavActivity(): MainNavActivity

    @ContributesAndroidInjector(modules = [ViewModelModule::class])
    abstract fun contributeHomeFragment(): HomeFragment

    @ContributesAndroidInjector(modules = [ViewModelModule::class])
    abstract fun contributeDetailsFragment(): DetailsFragment
}