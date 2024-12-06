package com.generic.login.view.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.generic.login.adapter.LoadStateAdapter
import com.generic.login.databinding.FragmentDashboardBinding
import com.generic.login.adapter.ProductAdapter
import com.generic.login.view.base.BaseFragment
import com.generic.login.viewmodel.DashboardViewModel
import com.generic.login.adapter.ProductComparator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_dashboard.pb_dashboard
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class DashboardFragment : BaseFragment<FragmentDashboardBinding, DashboardViewModel>() {
    override val viewModel: DashboardViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        doInit()
    }

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentDashboardBinding.inflate(inflater, container, false)

    private fun doInit() {
        showProgressBar()

        val pagingAdapter = ProductAdapter(ProductComparator)
        binding.rvDashboard.adapter = pagingAdapter
        binding.rvDashboard.setHasFixedSize(true)

        lifecycleScope.launch {
            viewModel.getPhotosPaged().collectLatest { pagingData ->
                hideProgressBar()
                pagingAdapter.submitData(pagingData)
            }
        }
        binding.rvDashboard.adapter =
            pagingAdapter.withLoadStateHeaderAndFooter(
                header = LoadStateAdapter { pagingAdapter.retry() },
                footer = LoadStateAdapter { pagingAdapter.retry() }
            )
    }

    private fun showProgressBar() {
        pb_dashboard.visibility = View.VISIBLE
    }

    private fun hideProgressBar() {
        pb_dashboard.visibility = View.GONE
    }
}