package com.android.cleanarchitectureshoppingapp.domain

import com.android.cleanarchitectureshoppingapp.data.repository.ProductRepository

class DeleteOrderedProductListUseCase(
    private val productRepository: ProductRepository
):UseCase {
    suspend operator fun invoke(){
        return productRepository.deleteAll()

    }

}