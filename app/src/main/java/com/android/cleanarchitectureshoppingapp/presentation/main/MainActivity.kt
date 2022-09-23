package com.android.cleanarchitectureshoppingapp.presentation.main

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Button
import androidx.fragment.app.Fragment
import com.android.cleanarchitectureshoppingapp.R
import com.android.cleanarchitectureshoppingapp.databinding.ActivityMainBinding
import com.android.cleanarchitectureshoppingapp.presentation.BaseActivity
import com.android.cleanarchitectureshoppingapp.presentation.BaseFragment
import com.android.cleanarchitectureshoppingapp.presentation.list.ProductListFragment
import com.android.cleanarchitectureshoppingapp.presentation.profile.ProfileFragment
import com.google.android.material.bottomnavigation.BottomNavigationItemView
import com.google.android.material.bottomnavigation.BottomNavigationMenuView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

internal class MainActivity : BaseActivity<MainViewModel, ActivityMainBinding>(),BottomNavigationView.OnNavigationItemSelectedListener       {
    override val viewModel by viewModel<MainViewModel>()
    override fun getViewBinding(): ActivityMainBinding = ActivityMainBinding.inflate(layoutInflater)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViews()
    }

    private fun initViews() = with(binding) {
        bottomNavigationView.setOnItemSelectedListener(this@MainActivity)
        showFragment(ProductListFragment(), ProductListFragment.TAG)
    }


    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        Log.d("sdfafasfa", "dasfsafdsfas")
        return when (item.itemId) {
            R.id.menu_products -> {
                showFragment(ProductListFragment(), ProductListFragment.TAG)
                Log.d("adsf", "1")
                true

            }
            R.id.menu_profile -> {
                showFragment(ProfileFragment(), ProfileFragment.TAG)
                Log.d("adsf", "2")
                true
            }
            else -> false
        }
    }




    private fun showFragment(fragment: Fragment, tag: String) {
        val findFragment = supportFragmentManager.findFragmentByTag(tag)
        supportFragmentManager.fragments.forEach { fm ->
            supportFragmentManager.beginTransaction().hide(fm).commit()
        }
        findFragment?.let {
            supportFragmentManager.beginTransaction().show(it).commit()

        } ?: kotlin.run {
            supportFragmentManager.beginTransaction()
                .add(R.id.fragment_container_view, fragment, tag)
                .commitAllowingStateLoss()
        }
    }

    override fun observeData() = viewModel.mainStateLiveData.observe(this) {
        when (it) {
            is MainState.RefreshOrderList -> {
                binding.bottomNavigationView.selectedItemId = R.id.menu_profile
                val fragment = supportFragmentManager.findFragmentByTag(ProfileFragment.TAG)
                (fragment as? BaseFragment<*, *>)?.viewModel?.fetchData()
            }
        }
    }

}