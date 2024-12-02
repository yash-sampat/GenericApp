package com.generic.login.view.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.generic.login.R
import com.generic.login.databinding.FragmentProductDetailBinding
import com.generic.login.model.products.Product
import com.generic.login.view.base.BaseFragment
import com.generic.login.viewmodel.ProductDetailViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_product_detail.view.productDetailImage
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class ProductDetailFragment : BaseFragment<FragmentProductDetailBinding, ProductDetailViewModel>() {
    override val viewModel: ProductDetailViewModel by viewModels()
    private val args: ProductDetailFragmentArgs by navArgs()
    private lateinit var product: Product

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        product = args.product
    }

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentProductDetailBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Glide.with(view.context).load(product.largeImageURL).centerCrop().into(view.productDetailImage)
        binding.apply {
            productModel = product
        }
    }
}