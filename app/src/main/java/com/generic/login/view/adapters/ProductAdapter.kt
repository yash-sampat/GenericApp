package com.generic.login.view.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.generic.login.R
import com.generic.login.model.products.Product

class ProductAdapter(private val data: List<Product>) : RecyclerView.Adapter<ProductAdapter.ViewHolder>()  {

    class ViewHolder(val view: View): RecyclerView.ViewHolder(view){

        fun bind(product: Product){
            val title = view.findViewById<TextView>(R.id.tvTitle)
            val imageView = view.findViewById<ImageView>(R.id.imageView)
            val description = view.findViewById<TextView>(R.id.tvDescription)

            title.text = product.tags
            description.text = product.user

            Glide.with(view.context).load(product.previewURL).centerCrop().into(imageView)

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_recycler, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position])
    }


}