package com.semba.weatherlogger.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import javax.inject.Inject
import javax.inject.Provider
import javax.inject.Singleton

/**
 * Created by SeMbA on 2020-01-02.
 */
@Singleton
class ViewModelProviderFactory @Inject constructor(private val creators: Map< Class<out ViewModel>, @JvmSuppressWildcards Provider<ViewModel>>) : ViewModelProvider.NewInstanceFactory() {

    private val TAG = "VIEW_MODEL_FACTORY"

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        var creator: Provider<out ViewModel>? = creators[modelClass]
        if (creator == null) { // ViewModel has not been created

            // Check if the Class is represented by any Key in the map.
            for ((key, value) in creators) {

                if (modelClass.isAssignableFrom(key)) {
                    creator = value
                    break
                }
            }
        }

        // throw exception (No ViewModel)
        if (creator == null) {
            throw IllegalArgumentException("$TAG : unknown model class $modelClass")
        }

        // Provide the ViewModel
        try {
            return creator.get() as T
        } catch (e: Exception) {
            throw RuntimeException(e)
        }

    }

}