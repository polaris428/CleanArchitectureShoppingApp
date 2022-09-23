package com.android.cleanarchitectureshoppingapp.presentation.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.android.cleanarchitectureshoppingapp.domain.GetProductListUseCase
import com.android.cleanarchitectureshoppingapp.presentation.BaseViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

internal class ProductListViewModel(
   private var getProductListUseCase: GetProductListUseCase
):BaseViewModel() {
    private var _productListStATELiveData= MutableLiveData<ProductListState>(ProductListState.UnInitialized)
    var productListStAteLiveData:LiveData<ProductListState> = _productListStATELiveData
    override fun fetchData(): Job =viewModelScope.launch {
        setState(
            ProductListState.Loading
        )
        setState(
            ProductListState.Success(
                getProductListUseCase()
            )
        )
    }
    private fun setState(state: ProductListState){
        _productListStATELiveData.postValue(state)
    }
}