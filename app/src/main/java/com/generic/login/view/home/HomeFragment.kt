package com.generic.login.view.home

import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.generic.login.adapter.LoadStateAdapter
import com.generic.login.adapter.ProductAdapter
import com.generic.login.adapter.ProductComparator
import com.generic.login.databinding.FragmentHomeBinding
import com.generic.login.view.base.BaseFragment
import com.generic.login.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding, HomeViewModel>() {
    override val viewModel: HomeViewModel by viewModels()
    private val listState: String = "listState"

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentHomeBinding.inflate(inflater, container, false)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        retainInstance = true
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showInfiniteScrollingList()
    }

    private fun showInfiniteScrollingList() = with(binding){
        if(recyclerView.adapter == null) {
            val pagingAdapter = ProductAdapter(ProductComparator)
            pagingAdapter.stateRestorationPolicy = RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
            recyclerView.adapter =
                pagingAdapter.withLoadStateHeaderAndFooter(
                    header = LoadStateAdapter { pagingAdapter.retry() },
                    footer = LoadStateAdapter { pagingAdapter.retry() }
                )
            recyclerView.setHasFixedSize(true)
            lifecycleScope.launch {
                viewModel.getPhotosPaged().collectLatest { pagingData ->
                    pagingAdapter.submitData(pagingData)
                }
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        val mListState: Parcelable? = binding.recyclerView.layoutManager?.onSaveInstanceState()
        outState.putParcelable(listState, mListState)
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)

        if (savedInstanceState != null) {
            binding.recyclerView.layoutManager?.onRestoreInstanceState(savedInstanceState.getBundle(listState))
        }
    }
}