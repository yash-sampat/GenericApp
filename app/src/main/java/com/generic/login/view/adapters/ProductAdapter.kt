package com.generic.login.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.generic.login.databinding.ItemRecyclerBinding
import com.generic.login.model.products.Product
import com.generic.login.view.dashboard.DashboardFragmentDirections


class ProductAdapter(private val data: List<Product>) : RecyclerView.Adapter<ProductAdapter.ViewHolder>()  {

    class ViewHolder(val binding: ItemRecyclerBinding): RecyclerView.ViewHolder(binding.root){

        fun bind(product: Product, position: Int) = with(binding){
            productsModel = product
            Glide.with(binding.root).load(product.previewURL).centerCrop().into(binding.imageView)

            binding.root.setOnClickListener{
                val direction = DashboardFragmentDirections.actionDashboardFragmentToDetailFragment(product)
                it.findNavController().navigate(direction)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        //val v = LayoutInflater.from(parent.context).inflate(R.layout.item_recycler, parent, false)
        val v = ItemRecyclerBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position], position)
    }


}