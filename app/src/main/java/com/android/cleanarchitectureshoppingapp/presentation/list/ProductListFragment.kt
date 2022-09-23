package com.android.cleanarchitectureshoppingapp.presentation.list

import android.app.Instrumentation
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isGone
import com.android.cleanarchitectureshoppingapp.R
import com.android.cleanarchitectureshoppingapp.databinding.FragmentProductListBinding
import com.android.cleanarchitectureshoppingapp.databinding.FragmentProfileBinding
import com.android.cleanarchitectureshoppingapp.extensions.toast
import com.android.cleanarchitectureshoppingapp.presentation.BaseFragment
import com.android.cleanarchitectureshoppingapp.presentation.adapter.ProductListAdapter
import com.android.cleanarchitectureshoppingapp.presentation.detail.ProductDetailActivity
import com.android.cleanarchitectureshoppingapp.presentation.main.MainActivity
import com.android.cleanarchitectureshoppingapp.presentation.profile.ProfileViewModel
import org.koin.android.ext.android.inject
import org.koin.java.KoinJavaComponent.inject


internal class ProductListFragment : BaseFragment<ProductListViewModel,FragmentProductListBinding>(){
    companion object {
        const val TAG="ProductListFragment"
    }

    override val viewModel by  inject<ProductListViewModel>()

    override fun getViewBinding(): FragmentProductListBinding = FragmentProductListBinding.inflate(layoutInflater)
    private val adapter=ProductListAdapter()
    private val startProductDetailForResult=
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            result:ActivityResult->
            if(result.resultCode == ProductDetailActivity.PRODUCT_ORDERED_RESULT_CODE){
                (requireActivity() as MainActivity).viewModel.refreshOrderList()

            }
        }
    override fun observeData() =viewModel.productListStAteLiveData.observe(this){
        when(it){
            is ProductListState.UnInitialized->{
                initView(binding)
            }
            is  ProductListState.Loading -> {
                handleLoadingState()
            }
            is ProductListState.Success->{
                handleSuccessState(it)
            }

            ProductListState.Error -> {
                handleErrorState()
            }

        }
    }

    private fun initView(binding: FragmentProductListBinding) = with(binding){
        recyclerView.adapter=adapter
        refreshLayout.setOnRefreshListener {
            viewModel.fetchData()
        }
    }
    private fun handleLoadingState()= with(binding){
        refreshLayout.isRefreshing=false
        Log.d("로로로로","safdsfa")
    }
    private fun handleSuccessState(state: ProductListState.Success)= with(binding){
        refreshLayout.isRefreshing=false

        if(state.productList.isEmpty()){
            Log.d("asdfsad","safdsfa")
            emptyResultTextView.isGone=false
            recyclerView.isGone=true

        }else{
            Log.d("aaaaa","bbb")
            emptyResultTextView.isGone=true
            recyclerView.isGone=false
            adapter.setProductList(state.productList){
                startProductDetailForResult.launch(
                    ProductDetailActivity.newIntent(requireContext(),it.id)

                )


            }
        }

    }

    private fun handleErrorState(){
        Toast.makeText(requireContext(),"에러가 발생했습니다",Toast.LENGTH_SHORT).show()
    }

}