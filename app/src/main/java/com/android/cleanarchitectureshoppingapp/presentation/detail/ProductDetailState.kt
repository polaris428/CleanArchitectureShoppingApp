package com.android.cleanarchitectureshoppingapp.presentation.detail

import com.android.cleanarchitectureshoppingapp.data.entity.product.ProductEntity
import com.android.cleanarchitectureshoppingapp.presentation.list.ProductListState

sealed class ProductDetailState {
    object UnInitialized:ProductDetailState()

    object Loading:ProductDetailState()

    data class Success(
        val productEntity: ProductEntity
    ):ProductDetailState()

    object Order:ProductDetailState()

    object Error:ProductDetailState()




}