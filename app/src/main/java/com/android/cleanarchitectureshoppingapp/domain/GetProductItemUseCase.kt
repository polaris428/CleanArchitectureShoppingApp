package com.android.cleanarchitectureshoppingapp.domain

import com.android.cleanarchitectureshoppingapp.data.entity.product.ProductEntity
import com.android.cleanarchitectureshoppingapp.data.repository.DefaultProductRepository
import com.android.cleanarchitectureshoppingapp.data.repository.ProductRepository

internal class GetProductItemUseCase(
    private var productRepository: ProductRepository
) :UseCase{
    suspend operator fun invoke(productId:Long):ProductEntity?{
        return  productRepository.getProductItem(productId)
    }
}