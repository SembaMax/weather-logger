package com.semba.weatherlogger.ui.detailsScreen

import android.os.Bundle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import com.semba.weatherlogger.BR
import com.semba.weatherlogger.R
import com.semba.weatherlogger.base.BaseFragment
import com.semba.weatherlogger.databinding.FragmentDetailsBinding
import com.semba.weatherlogger.utils.ViewModelProviderFactory
import javax.inject.Inject

class DetailsFragment : BaseFragment<FragmentDetailsBinding, DetailsViewModel>(), DetailsNavigator {

    @Inject
    lateinit var viewModelFactory: ViewModelProviderFactory
    private val args: DetailsFragmentArgs by navArgs()

    override fun getLayoutId(): Int {
        return R.layout.fragment_details
    }

    override fun getBindingVariable(): Int {
        return BR.viewModel
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        this.assignViewModel(viewModelFactory, DetailsViewModel::class.java)
        super.onCreate(savedInstanceState)
        this.mViewModel.setNavigator(this)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        initArgs()
    }

    /**
     * Get fragment's arguments which defined in navigation graph.
     */
    private fun initArgs() {
        mViewModel.forecast = args.forecast
        invalidate()
    }

    override fun toggleLoading(isLoading: Boolean) {

    }

    override fun showErrorMessage(message: String?) {
        Snackbar.make(view!!,message?: getString(R.string.error), Snackbar.LENGTH_SHORT).show()
    }

    override fun showErrorMessage(messageSrc: Int) {
        Snackbar.make(view!!,messageSrc, Snackbar.LENGTH_SHORT).show()
    }

    override fun navigateBack() {
        findNavController().popBackStack()
    }
}
