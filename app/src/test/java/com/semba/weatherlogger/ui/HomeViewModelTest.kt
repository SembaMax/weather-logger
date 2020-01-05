package com.semba.weatherlogger.ui

import androidx.lifecycle.MutableLiveData
import com.semba.weatherlogger.api.ApiService
import com.semba.weatherlogger.data.dataSources.OfflineWeatherDataSource
import com.semba.weatherlogger.data.dataSources.RemoteWeatherDataSource
import com.semba.weatherlogger.data.database.AppDatabase
import com.semba.weatherlogger.data.database.dao.ForecastDao
import com.semba.weatherlogger.data.entities.ForecastEntity
import com.semba.weatherlogger.data.models.*
import com.semba.weatherlogger.data.repositories.WeatherRepository
import com.semba.weatherlogger.rules.InstantTaskExecutorRule
import com.semba.weatherlogger.ui.homeScreen.HomeNavigator
import com.semba.weatherlogger.ui.homeScreen.HomeViewModel
import com.semba.weatherlogger.utils.Constants
import com.semba.weatherlogger.utils.Logger
import com.semba.weatherlogger.utils.SharedPreferencesManager
import com.semba.weatherlogger.utils.TestSchedulerProvider
import io.reactivex.Observable
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mockito.*


/**
 * Created by SeMbA on 2020-01-05.
 */
@ExtendWith(
    InstantTaskExecutorRule::class
)
class HomeViewModelTest {

    lateinit var repository: WeatherRepository
    lateinit var navigator: HomeNavigator
    lateinit var scheduler: TestSchedulerProvider
    lateinit var logger: Logger
    lateinit var preferences: SharedPreferencesManager
    lateinit var viewModel: HomeViewModel
    lateinit var apiService: ApiService
    lateinit var remoteWeatherDataSource: RemoteWeatherDataSource
    lateinit var offlineWeatherDataSource: OfflineWeatherDataSource
    lateinit var db: AppDatabase

    private val londonLat = 51.5074
    private val londonLong = 0.1278
    private val wrongParameter = -1.0
    private val weatherInfo = ForecastResponse(1,"London", arrayListOf(WeatherInfo(1,"","","")),
        MainInfo(10f,10f,10f,10f,10f,10f),
        WindInfo(10f,10f), CloudsInfo(1)
    )
    private val errorMessage = "Error"

    private val localEntities = arrayListOf(ForecastEntity(1,"","",1f,1f,1f,1f,1f,
        1f,1f,1f,1),
        ForecastEntity(2,"","",1f,1f,1f,1f,1f,
        1f,1f,1f,1))



    @BeforeEach
    fun setup()
    {
        apiService = mock(ApiService::class.java, RETURNS_DEEP_STUBS)
        `when`(apiService.getWeatherInfo(londonLat, londonLong)).thenReturn(Observable.just(weatherInfo))
        `when`(apiService.getWeatherInfo(wrongParameter, wrongParameter)).thenReturn(Observable.error(Throwable(errorMessage)))

        remoteWeatherDataSource = RemoteWeatherDataSource(apiService)
        db = mock(AppDatabase::class.java)
        val dao = mock(ForecastDao::class.java)
        offlineWeatherDataSource = OfflineWeatherDataSource(db)

        `when`(db.forecastDao()).thenReturn(dao)
        `when`(db.forecastDao().loadAll()).thenReturn(MutableLiveData<List<ForecastEntity>>(localEntities))
        `when`(db.forecastDao().insert(localEntities[0])).thenReturn(1L)
        `when`(db.forecastDao().insert(localEntities[1])).thenReturn(Constants.FAILED_INSERTION)
        `when`(db.forecastDao().deleteItem(localEntities[0])).thenReturn(Constants.SUCCESS_DELETION)
        `when`(db.forecastDao().deleteItem(localEntities[1])).thenReturn(Constants.FAILED_DELETION)

        repository = WeatherRepository(remoteWeatherDataSource,offlineWeatherDataSource)

        navigator = mock(HomeNavigator::class.java)
        preferences = mock(SharedPreferencesManager::class.java)
        scheduler = TestSchedulerProvider()
        logger = mock(Logger::class.java)

        viewModel = HomeViewModel(repository, scheduler, logger, preferences)
        viewModel.setNavigator(navigator)
    }

    @AfterEach
    fun after()
    {

    }

    @Test
    fun `Execute Logic`()
    {
        val spy = spy(viewModel)
        spy.executeLogic()
        verify(spy).loadCachedForecast()
        verify(navigator).reloadRecyclerView(ArrayList(localEntities.reversed()))
    }

    @Test
    fun `Fetch Weather Success`()
    {
        val spy = spy(viewModel)
        val order = inOrder(spy)
        spy.fetchLatestForecast(londonLat, londonLong)
        order.verify(spy).onNext(weatherInfo)
        assert(spy.latestForecast.value != null)
    }

    @Test
    fun `Fetch Weather Failure`()
    {
        val spy = spy(viewModel)
        val order = inOrder(spy)
        spy.fetchLatestForecast(wrongParameter, wrongParameter)
        order.verify(spy).onError(errorMessage)
        verify(navigator).showErrorMessage(errorMessage)
    }

    @Test
    fun `Update Widget`()
    {
        viewModel.latestForecast.value = weatherInfo
        verify(navigator)?.updateWidget(weatherInfo.main!!.temp.toString(),
            weatherInfo.name!!,weatherInfo.weather!!.first().description!!,weatherInfo.main!!.feelsLike.toString())
    }

    @Test
    fun `Load Database Items`()
    {
        val spy = spy(viewModel)
        spy.loadCachedForecast()
        assert(spy.localForecastItems != null)
        verify(spy).toggleLoading(true)
        verify(navigator).reloadRecyclerView(ArrayList(localEntities.reversed()))
    }

    @Test
    fun `Insert Database Item Success`()
    {
        val spy = spy(viewModel)
        spy.insertNewRecord(localEntities[0])
        verify(spy).loadCachedForecast()
    }

    @Test
    fun `Insert Database Item Failure`()
    {
        viewModel.insertNewRecord(localEntities[1])
        verify(navigator).showErrorMessage(Constants.DEFAULT_ERROR)
    }

    @Test
    fun `Delete Database Item Success`()
    {
        val spy = spy(viewModel)
        spy.deleteForecast(localEntities[0])
        verify(spy).loadCachedForecast()
    }

    @Test
    fun `Delete Database Item Failure`()
    {
        viewModel.deleteForecast(localEntities[1])
        verify(navigator).showErrorMessage(Constants.DEFAULT_ERROR)
    }

}