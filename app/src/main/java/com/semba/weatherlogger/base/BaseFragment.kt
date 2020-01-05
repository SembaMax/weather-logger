package com.semba.weatherlogger.base

import android.annotation.TargetApi
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.checkSelfPermission
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import dagger.android.support.DaggerFragment

/**
 * Created by SeMbA on 2020-01-02.
 */
abstract class BaseFragment <T: ViewDataBinding, V: ViewModel> : DaggerFragment() {

    var mActivity: BaseActivity<*, *>? = null
    lateinit var mRootView: View
    lateinit var mViewDataBinding: T
    lateinit var mViewModel: V

    abstract fun getLayoutId(): Int
    abstract fun getBindingVariable(): Int

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is BaseActivity<*, *>) {
            this.mActivity = context
        }
    }

    override fun onDetach() {
        mActivity = null
        super.onDetach()
    }

    fun assignViewModel(viewModelFactory: ViewModelProvider.Factory, vmClass: Class<V>): V {
        mViewModel = ViewModelProviders.of(this, viewModelFactory).get(vmClass)
        return mViewModel
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    private fun initBinding() {
        mViewDataBinding.setVariable(getBindingVariable(), mViewModel)
        mViewDataBinding.executePendingBindings()
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
        return (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) || checkSelfPermission(activity!!, permission) == PackageManager.PERMISSION_GRANTED
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mViewDataBinding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false)
        mRootView = mViewDataBinding.root
        initBinding()
        return mRootView
    }

    fun isNetworkAvailable() = mActivity?.isNetworkAvailable()

    fun invalidate() {
        mViewDataBinding.invalidateAll()
    }
}
