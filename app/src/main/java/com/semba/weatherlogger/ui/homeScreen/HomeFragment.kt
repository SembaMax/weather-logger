package com.semba.weatherlogger.ui.homeScreen

import android.Manifest
import android.annotation.SuppressLint
import android.appwidget.AppWidgetManager
import android.content.ComponentName
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.material.snackbar.Snackbar
import com.semba.weatherlogger.BR

import com.semba.weatherlogger.R
import com.semba.weatherlogger.base.BaseActivity
import com.semba.weatherlogger.base.BaseFragment
import com.semba.weatherlogger.data.entities.ForecastEntity
import com.semba.weatherlogger.data.models.ForecastResponse
import com.semba.weatherlogger.databinding.FragmentHomeBinding
import com.semba.weatherlogger.ui.homeScreen.adapters.ForecastAdapter
import com.semba.weatherlogger.utils.Constants
import com.semba.weatherlogger.utils.MarginItemDecoration
import com.semba.weatherlogger.utils.ViewModelProviderFactory
import com.semba.weatherlogger.widget.WeatherLoggerWidget
import kotlinx.android.synthetic.main.fragment_home.*
import javax.inject.Inject

class HomeFragment : BaseFragment<FragmentHomeBinding, HomeViewModel>(), HomeNavigator {

    @Inject
    lateinit var viewModelFactory: ViewModelProviderFactory
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    private var adapter: ForecastAdapter? = null

    companion object{
        const val LOCATION_PERMISSION_CODE = 3
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_home
    }

    override fun getBindingVariable(): Int {
        return BR.viewModel
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        this.assignViewModel(viewModelFactory, HomeViewModel::class.java)
        super.onCreate(savedInstanceState)
        this.mViewModel.setNavigator(this)
    }

    private fun getCurrentLocation() {
        if (checkPermissions()) {
            fusedLocationClient = LocationServices.getFusedLocationProviderClient(activity!!)
            fusedLocationClient.lastLocation
                .addOnSuccessListener { location: Location? ->
                    if (location != null) {
                        mViewModel.location.value = location
                    }
                }
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        getCurrentLocation()
        setupForecastList()
        Handler().post {
            mViewModel.executeLogic()
        }
    }

    /**
     * Setup forecast recyclerView and adapter.
     */
    private fun setupForecastList() {
        forecast_recyclerView?.layoutManager = LinearLayoutManager(activity)
        forecast_recyclerView?.addItemDecoration(MarginItemDecoration(activity!!))

        adapter = ForecastAdapter(this)
        forecast_recyclerView?.adapter = adapter
    }

    @SuppressLint("RestrictedApi")
    override fun toggleLoading(isLoading: Boolean) {
        progress?.visibility = if(isLoading) View.VISIBLE else View.GONE
        save_btn?.visibility = if(isLoading) View.GONE else View.VISIBLE
    }

    override fun showErrorMessage(message: String?) {
        Snackbar.make(view!!,message?: getString(R.string.error), Snackbar.LENGTH_SHORT).show()
    }

    override fun showErrorMessage(messageSrc: Int) {
        Snackbar.make(view!!,messageSrc,Snackbar.LENGTH_SHORT).show()
    }

    private fun checkPermissions(): Boolean {
        if (!hasPermissions(Manifest.permission.ACCESS_COARSE_LOCATION))
        {
            performPermissions(arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION), LOCATION_PERMISSION_CODE)
            return false
        }

        return true
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == LOCATION_PERMISSION_CODE)
        {
            if (grantResults.any { it ==  PackageManager.PERMISSION_GRANTED})
            {
                getCurrentLocation()
            }
            else
            {
                showErrorMessage("Couldn't get weather info, Please approve location permission")
            }
        }
    }

    override fun navigateToDetailsScreen(forecastItem: ForecastEntity) {
        mViewModel.mCompositeDisposable.clear()
        val action = HomeFragmentDirections.actionHomeFragmentToDetailsFragment(forecastItem)
        findNavController().navigate(action)
    }

    override fun invalidateUI() {
        invalidate()
    }

    override fun reloadRecyclerView(items: ArrayList<ForecastEntity>) {
        adapter?.setForecastItems(items)
    }

    override fun updateWidget(temp: String, name: String, description: String, feelsLike: String) {
        val widgetManager = AppWidgetManager.getInstance(activity)
        val ids = widgetManager.getAppWidgetIds(
            ComponentName(activity!!, WeatherLoggerWidget::class.java)
        )

        val updateIntent = Intent(AppWidgetManager.ACTION_APPWIDGET_UPDATE)
        updateIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, ids)
        activity?.sendBroadcast(updateIntent)
    }

    override fun deleteForecast(forecastItem: ForecastEntity) {
        mViewModel.deleteForecast(forecastItem)
    }

    override fun requestPermission() {
        checkPermissions()
    }
}