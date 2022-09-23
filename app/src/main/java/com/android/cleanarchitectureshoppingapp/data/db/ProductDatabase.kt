package com.android.cleanarchitectureshoppingapp.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.android.cleanarchitectureshoppingapp.data.db.dao.ProductDao
import com.android.cleanarchitectureshoppingapp.data.entity.product.ProductEntity
import com.android.cleanarchitectureshoppingapp.utilluty.DateConverter

@Database(
    entities = [ProductEntity::class],
    version = 1,
    exportSchema = false
)

@TypeConverters(DateConverter::class)
abstract class ProductDatabase:RoomDatabase() {
    companion object{
        const val DB_NAME="ProductDataBase.db"
    }
    abstract fun  productDao(): ProductDao
}