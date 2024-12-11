package com.generic.login.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.generic.login.databinding.ItemRecyclerBinding
import com.generic.login.model.products.Product
import com.generic.login.view.home.HomeFragmentDirections


class ProductAdapter(diffCallback: DiffUtil.ItemCallback<Product>) : PagingDataAdapter<Product, ProductAdapter.ViewHolder>(
    diffCallback
)  {

    class ViewHolder(private val binding: ItemRecyclerBinding): RecyclerView.ViewHolder(binding.root){

        fun bind(product: Product, position: Int) = with(binding){
            productModel = product

            binding.root.setOnClickListener{
                val direction = HomeFragmentDirections.actionHomeFragmentToDetailFragment(product)
                it.findNavController().navigate(direction)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = ItemRecyclerBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it, position)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

}