package com.semba.weatherlogger.ui.detailsScreen

import com.semba.weatherlogger.base.BaseViewModel
import com.semba.weatherlogger.data.entities.ForecastEntity
import javax.inject.Inject

/**
 * Created by SeMbA on 2020-01-02.
 */
class DetailsViewModel @Inject constructor() : BaseViewModel<DetailsNavigator>() {

    var forecast: ForecastEntity? = null

    override fun executeLogic() {
        //The view syncs the forecast data via databinding
    }

    fun onBackClicked()
    {
        mNavigator?.get()?.navigateBack()
    }
}