package com.semba.weatherlogger.base

import android.annotation.TargetApi
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.semba.weatherlogger.utils.ConnectivityManager
import dagger.android.AndroidInjection
import dagger.android.support.DaggerAppCompatActivity

/**
 * Created by SeMbA on 2020-01-02.
 */
abstract class BaseActivity <T: ViewDataBinding, V: ViewModel>: DaggerAppCompatActivity() {

    lateinit var mViewDataBinding: T
    lateinit var mViewModel: V

    abstract fun getLayoutId(): Int
    abstract fun getBindingVariable(): Int
    abstract fun afterInjection()

    /**
     * Called from the activity's sub class to specify the required ViewModel class and to pass the injected Factory
     */
    fun assignViewModel(viewModelFactory: ViewModelProvider.Factory, vmClass: Class<V>): V
    {
        mViewModel = ViewModelProviders.of(this, viewModelFactory).get(vmClass)
        return mViewModel
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        afterInjection()
        super.onCreate(savedInstanceState)
        initBinding()
    }

    /**
     * Ask for the permissions on Android versions  >= M
     */
    @TargetApi(Build.VERSION_CODES.M)
    fun performPermissions(permissions: Array<String>, requestCode: Int)
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            requestPermissions(permissions ,requestCode)
    }

    /**
     * Check for the required permissions on Android versions  >= M
     */
    @TargetApi(Build.VERSION_CODES.M)
    fun hasPermissions(permission: String): Boolean
    {
        return (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) || checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED
    }

    /**
     * Network connectivity helper
     */
    fun isNetworkAvailable() = ConnectivityManager.isConnected(applicationContext)

    /**
     * setup the binding work
     */
    private fun initBinding()
    {
        mViewDataBinding = DataBindingUtil.setContentView(this, getLayoutId())
        mViewDataBinding.setVariable(getBindingVariable(), mViewModel)
        mViewDataBinding.executePendingBindings()
    }

    fun validate() {
        mViewDataBinding.invalidateAll()
    }

}