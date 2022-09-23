package com.android.cleanarchitectureshoppingapp.data.entity.product

import android.media.Image
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*
@Entity
data class ProductEntity(
    @PrimaryKey val id:Long,
    val createdAt:Date,
    val productName: String,
    val productPrice:Int,
    val productImage: String,
    val productType:String,
    val productIntroductionImage:String



)
