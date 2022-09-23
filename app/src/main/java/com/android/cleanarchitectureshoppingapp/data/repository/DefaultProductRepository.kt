package com.android.cleanarchitectureshoppingapp.data.repository

import com.android.cleanarchitectureshoppingapp.data.db.dao.ProductDao
import com.android.cleanarchitectureshoppingapp.data.entity.product.ProductEntity
import com.android.cleanarchitectureshoppingapp.data.network.ProductApiService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class DefaultProductRepository(
    private val productApi: ProductApiService,
    private val ioDispatcher: CoroutineDispatcher,
    private val productDao: ProductDao
):ProductRepository {
    override suspend fun getProductList(): List<ProductEntity> = withContext(ioDispatcher){
        val response=productApi.getProducts()
        return@withContext if (response.isSuccessful){
            response.body()?.items?.map { it.toEntity() }?: listOf()
        }else{
            listOf()
        }
    }

    override suspend fun getLocalProductList(): List<ProductEntity> = withContext(ioDispatcher) {
      productDao.getAll()
    }

    override suspend fun insertProductItem(ProductItem: ProductEntity): Long = withContext(ioDispatcher) {
       productDao.insert(ProductItem)
    }

    override suspend fun insertProductList(ProductList: List<ProductEntity>) = withContext(ioDispatcher) {
        TODO("Not yet implemented")
    }

    override suspend fun updateProductItem(ProductItem: ProductEntity) = withContext(ioDispatcher) {
        TODO("Not yet implemented")
    }

    override suspend fun getProductItem(itemId: Long): ProductEntity? = withContext(ioDispatcher) {
        val response=productApi.getProduct(itemId)
        return@withContext if (response.isSuccessful){
            response.body()?.toEntity()
        }else{
            null
        }
    }

    override suspend fun deleteAll() = withContext(ioDispatcher) {
        productDao.deleteAll()
    }

    override suspend fun deleteProductItem(id: Long) = withContext(ioDispatcher) {
        TODO("Not yet implemented")
    }
}