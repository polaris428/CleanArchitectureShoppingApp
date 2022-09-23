package com.android.cleanarchitectureshoppingapp.presentation.detail

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.view.isGone
import androidx.core.view.isVisible
import com.android.cleanarchitectureshoppingapp.R
import com.android.cleanarchitectureshoppingapp.databinding.ActivityProductDetailBinding
import com.android.cleanarchitectureshoppingapp.extensions.toast
import com.android.cleanarchitectureshoppingapp.presentation.BaseActivity
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

internal class ProductDetailActivity :
    BaseActivity<ProductDetailViewModel, ActivityProductDetailBinding>() {
    companion object {
        const val PRODUCT_ID_KEY = "PRODUCT_ID_KEY"
        const val PRODUCT_ORDERED_RESULT_CODE = 99
        fun newIntent(context: Context, productId: Long) =
            Intent(context, ProductDetailActivity::class.java).apply {
                putExtra(PRODUCT_ID_KEY, productId)
            }

    }


    override val viewModel by inject<ProductDetailViewModel>() {
        parametersOf(
            intent.getLongExtra(PRODUCT_ID_KEY,-1)
        )
    }

    override fun getViewBinding(): ActivityProductDetailBinding =
        ActivityProductDetailBinding.inflate(layoutInflater)


    override fun observeData() = viewModel.productDetailStateLiveData.observe(this) {

        when (it) {
            is ProductDetailState.UnInitialized -> initViews()
            is ProductDetailState.Loading->handleLoading()
            is ProductDetailState.Success->handleSuccess(it)
            is ProductDetailState.Error->handleError()
            is ProductDetailState.Order->handleOrder()
        }
    }

    private fun initViews() = with(binding){
        setSupportActionBar(toolbar)
        actionBar?.setDisplayUseLogoEnabled(true)
        actionBar?.setDisplayHomeAsUpEnabled(true)
        title=""
        toolbar.setNavigationOnClickListener {
            finish()
        }
        orderButton.setOnClickListener {
            viewModel.orderProduct()
        }

    }
    private fun handleLoading()= with(binding){
        progressBar.isVisible=true
    }
    private fun handleSuccess(state: ProductDetailState.Success)= with(binding){
        progressBar.isGone=true
        val product=state.productEntity
        title=product.productName
        productCategoryTextView.text=product.productName
        productPriceTextView.text="${product.productPrice}원 "
    }

    private fun handleError(){
        toast("제품 정보를 불로 올 수 없습니다")
        finish()
    }
    private fun handleOrder(){
        setResult(PRODUCT_ORDERED_RESULT_CODE)
        toast("성공적으로 구매가 완료되었습니다")
        finish()
    }

}