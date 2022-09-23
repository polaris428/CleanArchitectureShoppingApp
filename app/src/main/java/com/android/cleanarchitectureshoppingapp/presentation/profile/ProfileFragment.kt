package com.android.cleanarchitectureshoppingapp.presentation.profile

import android.app.Activity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isGone
import androidx.core.view.isVisible
import com.android.cleanarchitectureshoppingapp.R
import com.android.cleanarchitectureshoppingapp.databinding.FragmentProfileBinding
import com.android.cleanarchitectureshoppingapp.extensions.loadCenterCrop
import com.android.cleanarchitectureshoppingapp.extensions.toast
import com.android.cleanarchitectureshoppingapp.presentation.BaseFragment
import com.android.cleanarchitectureshoppingapp.presentation.adapter.ProductListAdapter
import com.android.cleanarchitectureshoppingapp.presentation.detail.ProductDetailActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthCredential
import com.google.firebase.auth.GoogleAuthProvider
import org.koin.android.ext.android.inject


internal class ProfileFragment : BaseFragment<ProfileViewModel, FragmentProfileBinding>() {
    companion object {
        const val TAG = "ProfileFragment"
    }

    override val viewModel: ProfileViewModel by inject<ProfileViewModel>()

    override fun getViewBinding(): FragmentProfileBinding =
        FragmentProfileBinding.inflate(layoutInflater)

    private val gso: GoogleSignInOptions by lazy {
            GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
    }

    private val gsc by lazy { GoogleSignIn.getClient(requireActivity(), gso) }

    private val firebaseAuth by lazy { FirebaseAuth.getInstance() }
    private val loginLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult())  { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
                try {
                    task.getResult(ApiException::class.java)?.let { account ->
                        viewModel.saveToken(account.idToken ?: throw Exception())
                    } ?: throw Exception()

                } catch (e: Exception) {

                    e.printStackTrace()
                }
            }else{
                Log.d(result.resultCode.toString(),"sdfafs${Activity.RESULT_OK}")
            }
        }

    private var  adapter=ProductListAdapter()
    override fun observeData() = viewModel.profileStateLiveData.observe(this) {
        when (it) {
            is ProfileState.Uninitialized -> initView()
            is ProfileState.Loading->handleLoadingState()
            is ProfileState.Login-> handleLoginState(it)
            is ProfileState.Success-> handleSuccessState(it)
            is ProfileState.Error-> handleErrorState()

        }
    }

    private fun initView() = with(binding) {
        recyclerView.adapter=adapter
        loginButton.setOnClickListener {
            signInGoogle()
        }
        logoutButton.setOnClickListener {
            viewModel.signOut()
        }

    }

    private fun handleLoadingState() = with(binding) {
        progressBar.isVisible = true
        loginRequiredGroup.isGone = true
    }

    private fun signInGoogle() {
        val signInIntent = gsc.signInIntent

        loginLauncher.launch(signInIntent)
    }

    private fun handleSuccessState(state: ProfileState.Success)= with(binding){
        progressBar.isGone=true
        when(state){
            is ProfileState.Success.Registered->{
                handleRegisteredState(state)
            }
            is ProfileState.Success.NoRegistered->{
                profileGroup.isGone=true
                loginRequiredGroup.isVisible=true

            }
        }
    }
    private fun handleLoginState(state: ProfileState.Login)= with(binding){
        progressBar.isVisible=true
        val credentail=GoogleAuthProvider.getCredential(state.idToken,null)
        firebaseAuth.signInWithCredential(credentail)
            .addOnCompleteListener (requireActivity()){ task->
                if(task.isSuccessful){
                    val user=firebaseAuth.currentUser
                    viewModel.setUserInfo(user)
                }else{
                    viewModel.setUserInfo(null)
                }

            }
    }
    private fun handleRegisteredState(state: ProfileState.Success.Registered) = with(binding) {
        profileGroup.isVisible = true
        loginRequiredGroup.isGone = true
        profileImageView.loadCenterCrop(state.profileImageUri.toString(), 60f)
        userNameTextView.text = state.userName

        if (state.productList.isEmpty()) {
            emptyResultTextView.isGone = false
            recyclerView.isGone = true
        } else {
            emptyResultTextView.isGone = true
            recyclerView.isGone = false
            adapter.setProductList(state.productList) {
                startActivity(
                    ProductDetailActivity.newIntent(requireContext(), it.id)
                )
            }
        }
    }

    private fun handleErrorState() {
        requireContext().toast("에러가 발생했습니다.")
    }



}