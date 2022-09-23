package com.android.cleanarchitectureshoppingapp.presentation.adapter

import android.content.DialogInterface
import android.content.Entity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.cleanarchitectureshoppingapp.data.entity.product.ProductEntity
import com.android.cleanarchitectureshoppingapp.databinding.ViewholderProductItemBinding
import com.android.cleanarchitectureshoppingapp.extensions.load
import com.android.cleanarchitectureshoppingapp.presentation.list.ProductListState

class ProductListAdapter : RecyclerView.Adapter<ProductListAdapter.ProductItemViewHolder>() {
    private var productList: List<ProductEntity> = listOf()
    private lateinit var productItemClickListener: (ProductEntity) -> Unit

    inner class ProductItemViewHolder(
        private var binding: ViewholderProductItemBinding,
        val productItemClickListener: (ProductEntity) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        fun binData(data: ProductEntity) = with(binding) {
            productPriceTextView.text=data.productName
            productImageView.load(data.productImage)
            productPriceTextView.text="${data.productPrice}원"
        }

        fun bindViews(data: ProductEntity) {
            binding.root.setOnClickListener {
                productItemClickListener(data)
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductItemViewHolder {
        val view =
            ViewholderProductItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductItemViewHolder(view, productItemClickListener)
    }

    override fun onBindViewHolder(holder: ProductItemViewHolder, position: Int) {
        holder.binData(productList[position])
        holder.bindViews(productList[position])
    }

    override fun getItemCount() = productList.size

    fun setProductList(productList: List<ProductEntity>, productItemClickListener: (ProductEntity) -> Unit={ }){
        this.productList=productList
        this.productItemClickListener=productItemClickListener
        notifyDataSetChanged()
    }
}