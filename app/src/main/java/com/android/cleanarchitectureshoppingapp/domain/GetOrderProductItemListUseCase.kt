package com.android.cleanarchitectureshoppingapp.domain

import com.android.cleanarchitectureshoppingapp.data.entity.product.ProductEntity
import com.android.cleanarchitectureshoppingapp.data.repository.DefaultProductRepository
import com.android.cleanarchitectureshoppingapp.data.repository.ProductRepository

internal class GetOrderProductItemListUseCase(
    private var productRepository: ProductRepository
) :UseCase{
    suspend operator fun invoke():List<ProductEntity>{
        return  productRepository.getLocalProductList()
    }
}