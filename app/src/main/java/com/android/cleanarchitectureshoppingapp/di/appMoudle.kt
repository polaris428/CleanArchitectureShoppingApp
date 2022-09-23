package com.android.cleanarchitectureshoppingapp.di

import com.android.cleanarchitectureshoppingapp.data.db.dao.provideDB
import com.android.cleanarchitectureshoppingapp.data.db.dao.provideToDoDao
import com.android.cleanarchitectureshoppingapp.data.network.buildOkHttpClient
import com.android.cleanarchitectureshoppingapp.data.network.provideGsonConverterFactory
import com.android.cleanarchitectureshoppingapp.data.network.provideProductApiService
import com.android.cleanarchitectureshoppingapp.data.network.provideProductRetrofit
import com.android.cleanarchitectureshoppingapp.data.preference.PreferenceManager
import com.android.cleanarchitectureshoppingapp.data.repository.DefaultProductRepository
import com.android.cleanarchitectureshoppingapp.data.repository.ProductRepository
import com.android.cleanarchitectureshoppingapp.domain.*
import com.android.cleanarchitectureshoppingapp.domain.GetOrderProductItemListUseCase
import com.android.cleanarchitectureshoppingapp.domain.GetProductItemUseCase
import com.android.cleanarchitectureshoppingapp.domain.GetProductListUseCase
import com.android.cleanarchitectureshoppingapp.domain.OrderProductItemUseCase
import com.android.cleanarchitectureshoppingapp.presentation.detail.ProductDetailViewModel
import com.android.cleanarchitectureshoppingapp.presentation.list.ProductListViewModel
import com.android.cleanarchitectureshoppingapp.presentation.main.MainViewModel
import com.android.cleanarchitectureshoppingapp.presentation.profile.ProfileViewModel
import kotlinx.coroutines.Dispatchers
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule= module {
    //viewModel
    viewModel { MainViewModel() }
    viewModel { ProductListViewModel(get()) }
    viewModel { ProfileViewModel(get(),get(),get()) }
    viewModel{(productId:Long) -> ProductDetailViewModel(productId,get(),get())}
    //Coroutines Dispatcher
    single {  Dispatchers.Main}
    single { Dispatchers.IO }

    //UseCase

    factory { GetProductItemUseCase(get()) }
    factory { GetProductListUseCase(get()) }
    factory { OrderProductItemUseCase(get()) }
    factory { GetOrderProductItemListUseCase(get()) }
    factory { DeleteOrderedProductListUseCase(get()) }
    //Repositories
    single<ProductRepository> {DefaultProductRepository(get(),get(),get())}


    single { provideGsonConverterFactory() }
    single { buildOkHttpClient() }
    single { provideProductRetrofit(get(),get ()) }
    single { provideProductApiService(get()) }

    single { PreferenceManager(androidContext()) }

    //Database
    single { provideDB(androidApplication()) }
    single { provideToDoDao(get()) }
}