package com.generic.login.view.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.generic.login.adapter.LoadStateAdapter
import com.generic.login.adapter.ProductAdapter
import com.generic.login.adapter.ProductComparator
import com.generic.login.databinding.FragmentDashboardBinding
import com.generic.login.view.base.BaseFragment
import com.generic.login.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentDashboardBinding, HomeViewModel>() {
    override val viewModel: HomeViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showInfiniteScrollingList()
    }

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentDashboardBinding.inflate(inflater, container, false)

    private fun showInfiniteScrollingList() = with(binding){
        val pagingAdapter = ProductAdapter(ProductComparator)
        rvDashboard.adapter = pagingAdapter
        rvDashboard.setHasFixedSize(true)

        lifecycleScope.launch {
            viewModel.getPhotosPaged().collectLatest { pagingData ->
                pagingAdapter.submitData(pagingData)
            }
        }
        rvDashboard.adapter =
            pagingAdapter.withLoadStateHeaderAndFooter(
                header = LoadStateAdapter { pagingAdapter.retry() },
                footer = LoadStateAdapter { pagingAdapter.retry() }
            )
    }
}