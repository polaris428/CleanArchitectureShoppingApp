package com.android.cleanarchitectureshoppingapp.presentation.profile

import android.net.Uri
import com.android.cleanarchitectureshoppingapp.data.entity.product.ProductEntity

sealed class ProfileState {
    object Uninitialized : ProfileState()
    object Loading : ProfileState()
    data class Login(val idToken: String) : ProfileState()

    sealed class Success :ProfileState(){
        data class Registered(
            val userName:String,
            val profileImageUri:Uri?,
            val  productList:List<ProductEntity> = listOf()
        ):Success()
        object NoRegistered:Success()
    }
    object Error:ProfileState()


}