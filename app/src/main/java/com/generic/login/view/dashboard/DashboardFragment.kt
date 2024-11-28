package com.generic.login.view.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.generic.login.databinding.FragmentDashboardBinding
import com.generic.login.utils.Resource
import com.generic.login.view.base.BaseFragment
import com.generic.login.viewmodel.DashboardViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_dashboard.pb_dashboard
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class DashboardFragment : BaseFragment<FragmentDashboardBinding, DashboardViewModel>() {
    override val viewModel: DashboardViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        doInit()
    }

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentDashboardBinding.inflate(inflater, container, false)

    private fun doInit() {
        viewModel.productData.observe(viewLifecycleOwner, Observer { event ->
            event.getContentIfNotHandled()?.let { response ->
                when (response) {
                    is Resource.Success -> {
                        hideProgressBar()
                        response.data?.let { productResponse ->
                            response.message?.let { toast(it) }
                        }
                    }

                    is Resource.Error -> {
                        hideProgressBar()
                    }

                    is Resource.Loading -> {
                        showProgressBar()
                    }
                }
            }
        })
        viewModel.getProducts()
    }

    private fun showProgressBar() {
        pb_dashboard.visibility = View.VISIBLE
    }

    private fun hideProgressBar() {
        pb_dashboard.visibility = View.GONE
    }
}