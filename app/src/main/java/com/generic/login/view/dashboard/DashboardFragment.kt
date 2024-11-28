package com.generic.login.view.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.generic.login.databinding.FragmentDashboardBinding
import com.generic.login.utils.Resource
import com.generic.login.view.adapters.ProductAdapter
import com.generic.login.view.base.BaseFragment
import com.generic.login.viewmodel.DashboardViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_dashboard.pb_dashboard
import kotlinx.android.synthetic.main.fragment_dashboard.rv_dashboard
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class DashboardFragment : BaseFragment<FragmentDashboardBinding, DashboardViewModel>() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var manager: RecyclerView.LayoutManager
    private lateinit var productAdapter: RecyclerView.Adapter<*>
    override val viewModel: DashboardViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        manager = LinearLayoutManager(activity)
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
                            //response.message?.let { toast(it) }
                            recyclerView = rv_dashboard.apply{
                                productAdapter = ProductAdapter(productResponse.hits)
                                layoutManager = manager
                                adapter = productAdapter
                            }
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