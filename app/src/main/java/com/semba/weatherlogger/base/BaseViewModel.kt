package com.semba.weatherlogger.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import java.lang.ref.WeakReference

/**
 * Created by SeMbA on 2020-01-02.
 */
abstract class BaseViewModel <N> : ViewModel() {

    private var isLoading: MutableLiveData<Boolean> = MutableLiveData()

    /**
     * Includes the logic that viewModel class is responsible of
     */
    abstract fun executeLogic()

    fun toggleLoading(loading: Boolean)
    {
        isLoading.value = loading
    }

    /**
     * Handles all the disposables objects that are used inside the viewModel
     */
    val mCompositeDisposable: CompositeDisposable = CompositeDisposable()

    /**
     * This navigator handles the communication between the viewModel and its View (activity or fragment).
     */
    var mNavigator: WeakReference<N>? = null

    /**
     * Observe on the value of isLoading to notify the view.
     */
    private val loadingObserver = Observer<Boolean> {
        (mNavigator?.get() as BaseNavigator?)?.toggleLoading(it)
    }

    init {
        isLoading.value = false
        isLoading.observeForever(loadingObserver)
    }

    /**
     * assign the desired navigator after initializing the viewModel.
     */
    fun setNavigator(navigator: N)
    {
        mNavigator = WeakReference(navigator)
    }

    /**
     * Dispose
     */
    override fun onCleared() {
        mCompositeDisposable.clear()
        mCompositeDisposable.dispose()
        super.onCleared()
    }

}