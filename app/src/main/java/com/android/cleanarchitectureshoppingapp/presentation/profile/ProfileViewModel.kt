package com.android.cleanarchitectureshoppingapp.presentation.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.android.cleanarchitectureshoppingapp.data.preference.PreferenceManager
import com.android.cleanarchitectureshoppingapp.domain.DeleteOrderedProductListUseCase
import com.android.cleanarchitectureshoppingapp.domain.GetOrderProductItemListUseCase
import com.android.cleanarchitectureshoppingapp.presentation.BaseViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

internal class ProfileViewModel(
    private val preferenceManager: PreferenceManager,
    private val getOrderProductItemListUseCase: GetOrderProductItemListUseCase,
    private val deleteOrderedProductListUseCase: DeleteOrderedProductListUseCase
) : BaseViewModel() {


    private var _profileStateLiveData = MutableLiveData<ProfileState>(ProfileState.Uninitialized)
    val profileStateLiveData: LiveData<ProfileState> = _profileStateLiveData

    override fun fetchData(): Job = viewModelScope.launch {
        setState(ProfileState.Loading)
        preferenceManager.getIdToken()?.let {
            setState(ProfileState.Login(it))
        } ?: kotlin.run {
            setState(ProfileState.Success.NoRegistered)
        }
    }

    private fun setState(state: ProfileState) {
        _profileStateLiveData.postValue(state)
    }

    fun saveToken(idToken: String) = viewModelScope.launch {
        withContext(Dispatchers.IO) {
            preferenceManager.putIdToken(idToken)
            fetchData()
        }
    }

    fun setUserInfo(firebaseUser:FirebaseUser?)=viewModelScope.launch{
        firebaseUser?.let { user->
            setState(ProfileState.Success.Registered(
                user.displayName?:"익명",
                user.photoUrl,
               getOrderProductItemListUseCase()
            ))
        }?:kotlin.run {
            setState(ProfileState.Success.NoRegistered)
        }
    }

    fun signOut()=viewModelScope.launch {
        withContext(Dispatchers.IO){
            preferenceManager.removedToken()

        }
        deleteOrderedProductListUseCase()
        fetchData()
    }


}