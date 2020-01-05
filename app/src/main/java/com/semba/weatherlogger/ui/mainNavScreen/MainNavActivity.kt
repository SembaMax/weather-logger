package com.semba.weatherlogger.ui.mainNavScreen

import android.os.Bundle
import androidx.navigation.findNavController
import com.google.android.material.snackbar.Snackbar
import com.semba.weatherlogger.BR
import com.semba.weatherlogger.R
import com.semba.weatherlogger.base.BaseActivity
import com.semba.weatherlogger.databinding.ActivityMainNavBinding
import com.semba.weatherlogger.utils.ViewModelProviderFactory
import kotlinx.android.synthetic.main.activity_main_nav.*
import javax.inject.Inject

class MainNavActivity : BaseActivity<ActivityMainNavBinding, MainNavViewModel>(), MainNavNavigator {

    @Inject
    lateinit var viewModelFactory: ViewModelProviderFactory

    override fun getLayoutId(): Int {
        return R.layout.activity_main_nav
    }

    override fun getBindingVariable(): Int {
        return BR.viewModel
    }

    override fun afterInjection() {
        this.assignViewModel(viewModelFactory, MainNavViewModel::class.java)
        this.mViewModel.setNavigator(this)
    }

    override fun onSupportNavigateUp() = findNavController(R.id.main_navigation_graph).navigateUp()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViews()
    }

    private fun initViews() {

    }

    override fun toggleLoading(isLoading: Boolean) {

    }

    override fun showErrorMessage(message: String?) {
        Snackbar.make(main_layout,message?: getString(R.string.error), Snackbar.LENGTH_SHORT).show()
    }

    override fun showErrorMessage(messageSrc: Int) {
        Snackbar.make(main_layout,messageSrc,Snackbar.LENGTH_SHORT).show()
    }
}
