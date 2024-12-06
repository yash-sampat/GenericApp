package com.generic.login.adapter

import androidx.recyclerview.widget.DiffUtil
import com.generic.login.model.products.Product

object ProductComparator : DiffUtil.ItemCallback<Product>() {
    override fun areItemsTheSame(
        oldItem: Product,
        newItem: Product
    ): Boolean {
        // 'id' is unique
        return oldItem.id == newItem.id

    }

    override fun areContentsTheSame(
        oldItem: Product,
        newItem: Product
    ): Boolean {
        return oldItem == newItem
    }
}