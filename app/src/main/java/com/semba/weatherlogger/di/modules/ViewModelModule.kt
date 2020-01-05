package com.semba.weatherlogger.di.modules

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.semba.weatherlogger.di.keys.ViewModelKey
import com.semba.weatherlogger.ui.detailsScreen.DetailsViewModel
import com.semba.weatherlogger.ui.homeScreen.HomeViewModel
import com.semba.weatherlogger.ui.mainNavScreen.MainNavViewModel
import com.semba.weatherlogger.utils.ViewModelProviderFactory
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

/**
 * Created by SeMbA on 2020-01-02.
 */
@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(MainNavViewModel::class)
    abstract fun bindMainNavViewModel(viewModel: MainNavViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(HomeViewModel::class)
    abstract fun bindStartViewModel(viewModel: HomeViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(DetailsViewModel::class)
    abstract fun bindUserInfoViewModel(viewModel: DetailsViewModel): ViewModel

    @Binds
    abstract fun bindViewModelFactory(viewModelFactory: ViewModelProviderFactory): ViewModelProvider.Factory
}