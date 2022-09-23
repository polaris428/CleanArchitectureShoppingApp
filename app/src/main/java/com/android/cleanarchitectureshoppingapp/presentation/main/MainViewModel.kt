package com.android.cleanarchitectureshoppingapp.presentation.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.android.cleanarchitectureshoppingapp.presentation.BaseViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

internal class MainViewModel: BaseViewModel() {

    override fun fetchData(): Job =viewModelScope.launch {}
    private var _mainStateLiveData= MutableLiveData<MainState>()
    val mainStateLiveData: LiveData<MainState> = _mainStateLiveData
    fun refreshOrderList(){
        _mainStateLiveData.postValue(MainState.RefreshOrderList)
    }
}