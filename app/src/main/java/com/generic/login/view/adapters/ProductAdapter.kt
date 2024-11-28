package com.generic.login.view.adapters

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.generic.login.R
import com.generic.login.model.products.Product
import kotlinx.android.synthetic.main.item_recycler.view.constraintLayout
import kotlinx.android.synthetic.main.item_recycler.view.imageView
import kotlinx.android.synthetic.main.item_recycler.view.tvDescription
import kotlinx.android.synthetic.main.item_recycler.view.tvTitle


class ProductAdapter(private val data: List<Product>, private val fragment: Fragment) : RecyclerView.Adapter<ProductAdapter.ViewHolder>()  {

    class ViewHolder(val view: View, val fragment: Fragment): RecyclerView.ViewHolder(view){

        fun bind(product: Product, position: Int){
            view.tvTitle.text = product.tags
            view.tvDescription.text = product.user

            Glide.with(view.context).load(product.previewURL).centerCrop().into(view.imageView)

            view.constraintLayout.setOnClickListener{
                val bundle = Bundle()
                bundle.putString("largeImageURL", product.largeImageURL)
                bundle.putInt("size", product.imageSize)
                bundle.putString("type", product.type)
                bundle.putString("tags", product.tags)
                bundle.putString("user", product.user)
                bundle.putInt("views", product.views)
                bundle.putInt("likes", product.likes)
                bundle.putInt("comments", product.comments)
                bundle.putInt("favorites", product.collections)
                bundle.putInt("downloads", product.downloads)

                fragment.findNavController().navigate(R.id.action_dashboardFragment_to_detailFragment, bundle)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_recycler, parent, false)
        return ViewHolder(v, fragment)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position], position)
    }


}