package com.android.cleanarchitectureshoppingapp.domain

import com.android.cleanarchitectureshoppingapp.data.entity.product.ProductEntity
import com.android.cleanarchitectureshoppingapp.data.repository.DefaultProductRepository
import com.android.cleanarchitectureshoppingapp.data.repository.ProductRepository

internal class OrderProductItemUseCase(
    private var productRepository: ProductRepository
) :UseCase{
    suspend operator fun invoke(productEntity: ProductEntity):Long{
        return  productRepository.insertProductItem(productEntity)
    }
}