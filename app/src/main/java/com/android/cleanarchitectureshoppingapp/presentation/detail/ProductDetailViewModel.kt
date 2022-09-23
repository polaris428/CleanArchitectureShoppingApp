package com.android.cleanarchitectureshoppingapp.presentation.detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.android.cleanarchitectureshoppingapp.data.entity.product.ProductEntity
import com.android.cleanarchitectureshoppingapp.domain.GetProductItemUseCase
import com.android.cleanarchitectureshoppingapp.domain.OrderProductItemUseCase
import com.android.cleanarchitectureshoppingapp.presentation.BaseViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

internal class ProductDetailViewModel(
    var productId: Long,
    private val getProductItemUseCase: GetProductItemUseCase,
    private val orderProductItemUseCase: OrderProductItemUseCase
) : BaseViewModel() {

    private var _productDetailStateLiveData =
        MutableLiveData<ProductDetailState>(ProductDetailState.UnInitialized)
    val productDetailStateLiveData: LiveData<ProductDetailState> = _productDetailStateLiveData
    private lateinit var productEntity: ProductEntity


    override fun fetchData(): Job = viewModelScope.launch {

        setState(ProductDetailState.Loading)
        getProductItemUseCase(productId = productId)?.let { product ->
            productEntity = product
            setState(ProductDetailState.Success(product))
        } ?: kotlin.run {
            setState(ProductDetailState.Error)
        }
    }

    fun orderProduct() = viewModelScope.launch {
        if (::productEntity.isInitialized) {
            Log.d(productId.toString() + "입니다ㅂ", productId.toString())
            val productId = orderProductItemUseCase(productEntity = productEntity)
            if (productEntity.id == productId) {
                setState(ProductDetailState.Order)
            }
        } else {

            setState(ProductDetailState.Error)
        }


    }

    private fun setState(state: ProductDetailState) {
        _productDetailStateLiveData.postValue(state)
        Log.d("상태 변경","상태 변경")
    }

}