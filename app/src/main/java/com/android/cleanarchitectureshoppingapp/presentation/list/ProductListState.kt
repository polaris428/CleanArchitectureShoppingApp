package com.android.cleanarchitectureshoppingapp.presentation.list

import com.android.cleanarchitectureshoppingapp.data.entity.product.ProductEntity

sealed class ProductListState {
    object UnInitialized:ProductListState()
    object Loading:ProductListState()

    data class Success(
        val productList: List<ProductEntity>
    ):ProductListState()

    object Error:ProductListState()

}