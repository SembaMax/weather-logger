package com.semba.weatherlogger.ui.homeScreen

import android.location.Location
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.semba.weatherlogger.R
import com.semba.weatherlogger.api.ApiCallback
import com.semba.weatherlogger.base.BaseViewModel
import com.semba.weatherlogger.data.entities.ForecastEntity
import com.semba.weatherlogger.data.models.ForecastResponse
import com.semba.weatherlogger.data.repositories.WeatherRepository
import com.semba.weatherlogger.utils.Constants
import com.semba.weatherlogger.utils.Logger
import com.semba.weatherlogger.utils.SharedPreferencesManager
import com.semba.weatherlogger.utils.rx.ISchedulerProvider
import io.reactivex.observers.DisposableObserver
import javax.inject.Inject
import kotlin.collections.ArrayList

/**
 * Created by SeMbA on 2020-01-02.
 */
class HomeViewModel @Inject constructor(private val repository: WeatherRepository,
                                        private val mSchedulerProvider: ISchedulerProvider,
                                        private val logger: Logger,
                                        private val preferences: SharedPreferencesManager
) : BaseViewModel<HomeNavigator>(), ApiCallback {

    var temp: String = ""
    var name: String = ""
    var feelsLike: String = ""
    var description: String = ""
    var latestForecast = MutableLiveData<ForecastResponse>()
    var localForecastItems: LiveData<List<ForecastEntity>>? = null
    var location = MutableLiveData<Location>()

    /**
     * Observe the changes of the current weather data, in order to notify the view.
     */
    private val forecastObserver = Observer<ForecastResponse> {
        val desc = it.weather?.first()?.description ?: ""
        val t = it.main?.temp?.toString() ?: ""
        val loc = it.name ?: ""
        val feels = it.main?.feelsLike?.toString() ?: ""
        updateDatabindingAttributes(t, loc, desc, feels)
        updateWidgetAttributes(t, loc, desc, feels)
    }

    /**
     * Observe the state of weather items which are fetched from the database.
     */
    private val localForecastItemsObserver = Observer<List<ForecastEntity>> {
        toggleLoading(false)
        mNavigator?.get()?.reloadRecyclerView(ArrayList(it.reversed()))
    }

    /**
     * Observe the changes of the current location, in order to notify the api service with the location.
     */
    private val locationObserver = Observer<Location> {
        mCompositeDisposable.clear()
        fetchLatestForecast(location.value?.latitude ?: 0.0, location.value?.longitude ?: 0.0)
    }

    init {
        latestForecast.observeForever(forecastObserver)
        location.observeForever(locationObserver)
    }

    /**
     * Cache the recent weather values in order to update the weather widget
     */
    private fun updateWidgetAttributes(t: String, loc: String, desc: String, feels: String) {
        preferences.saveString(name, SharedPreferencesManager.NAME_PREF)
        preferences.saveString(temp, SharedPreferencesManager.TEMP_PREF)
        preferences.saveString(description, SharedPreferencesManager.DESCRIPTION_PREF)
        preferences.saveString(feelsLike, SharedPreferencesManager.FEELS_LIKE_PREF)

        mNavigator?.get()?.updateWidget(t, loc, desc, feels)
    }

    /**
     * Set values to layout fields and refresh
     */
    private fun updateDatabindingAttributes(t: String, loc: String, desc: String, feels: String)
    {
        description = desc
        temp = t
        name = loc
        feelsLike = feels
        mNavigator?.get()?.invalidateUI()
    }

    override fun executeLogic() {
        loadCachedForecast()
    }

    /**
     * Fetch weather items from database
     */
    fun loadCachedForecast()
    {
        toggleLoading(true)
        localForecastItems?.removeObserver(localForecastItemsObserver)
        localForecastItems = repository.loadForecastCache()
        localForecastItems?.observeForever(localForecastItemsObserver)

    }

    /**
     * Click event of save button
     */
    fun onSaveClicked()
    {
        if (latestForecast.value != null) {
            insertNewRecord(latestForecast.value!!.toEntity())
        }
        else
        {
            mNavigator?.get()?.showErrorMessage(R.string.no_data)
            mNavigator?.get()?.requestPermission()
        }
    }

    /**
     * Add new forecast entity to database
     */
    fun insertNewRecord(entity: ForecastEntity) {
        toggleLoading(true)
        mCompositeDisposable.add(repository.insertForecast(entity).observeOn(mSchedulerProvider.ui())
            .subscribeOn(mSchedulerProvider.io()).subscribeWith(object :
                DisposableObserver<Long>() {
                override fun onComplete() {
                    logger.d("Observer: Completed")
                }

                override fun onNext(t: Long) {
                    if (t != Constants.FAILED_INSERTION)
                        loadCachedForecast()
                    else
                        mNavigator?.get()?.showErrorMessage(Constants.DEFAULT_ERROR)
                    logger.d(t.toString())
                }

                override fun onError(e: Throwable) {
                    logger.e(e.toString())
                }
            })
        )
    }

    /**
     * Delete specific forecast entity from database
     */
    fun deleteForecast(entity: ForecastEntity)
    {
        toggleLoading(true)
        mCompositeDisposable.add(repository.deleteForecast(entity).observeOn(mSchedulerProvider.ui())
            .subscribeOn(mSchedulerProvider.io()).subscribeWith(object :
                DisposableObserver<Int>() {
                override fun onComplete() {
                    logger.d("Observer: Completed")
                }

                override fun onNext(t: Int) {
                    if (t == Constants.SUCCESS_DELETION)
                        loadCachedForecast()
                    else
                        mNavigator?.get()?.showErrorMessage(Constants.DEFAULT_ERROR)
                    logger.d(t.toString())
                }

                override fun onError(e: Throwable) {
                    logger.e(e.toString())
                }
            })
        )
    }

    /**
     * Getting the weather data by calling weather api periodically.
     */
    fun fetchLatestForecast(latitude: Double, longitude: Double)
    {
        mCompositeDisposable.add(repository.callGetLatestCurrenciesApi(latitude, longitude)
            .observeOn(mSchedulerProvider.ui())
            .subscribeOn(mSchedulerProvider.io())
            .subscribeWith(object : DisposableObserver<ForecastResponse>(){
                override fun onComplete() {
                    this@HomeViewModel.onComplete()
                    logger.d("Observer: Completed")
                }

                override fun onNext(t: ForecastResponse) {
                    this@HomeViewModel.onNext(t)
                    logger.d(t.toString())
                }

                override fun onError(e: Throwable) {
                    this@HomeViewModel.onError(e.message ?: Constants.DEFAULT_ERROR)
                    logger.e(e.toString())
                }
            }))
    }

    override fun onNext(parameter: Any) {
        if (parameter is ForecastResponse)
        {
            toggleLoading(false)
            latestForecast.value = parameter
        }
    }

    override fun onComplete() {
        toggleLoading(false)
    }

    override fun onError(message: String) {
        toggleLoading(false)
        mNavigator?.get()?.showErrorMessage(message)
    }

    /**
     * Unsubscribe all our observers.
     */
    override fun onCleared() {
        latestForecast.removeObserver(forecastObserver)
        location.removeObserver(locationObserver)
        localForecastItems?.removeObserver(localForecastItemsObserver)
        super.onCleared()
    }

}