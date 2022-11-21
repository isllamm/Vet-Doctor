package com.tawajood.vetclinic.ui.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.tawajood.vetclinic.R
import com.tawajood.vetclinic.databinding.ActivityMainBinding
import com.tawajood.vetclinic.ui.auth.AuthActivity
import com.tawajood.vetclinic.utils.LoadingUtil
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {


    private lateinit var binding: ActivityMainBinding
    lateinit var navController: NavController
    private lateinit var loadingUtil: LoadingUtil


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        loadingUtil = LoadingUtil(this)

        onClick()
        setupNavController()

    }

    private fun onClick() {

        binding.toolbar.ivBack.setOnClickListener {
            navController.popBackStack()
            Log.d("islam", "back: i'm back")
        }
    }

    private fun setupNavController() {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment)
                as NavHostFragment
        navController = navHostFragment.navController

        binding.bottomNavView.setupWithNavController(navController)
    }

    fun logout() {
        PrefsHelper.setToken("")
        PrefsHelper.setUserId(-1)
        PrefsHelper.setPhone("")
        PrefsHelper.setUsername("")
        PrefsHelper.setUserImage("")
        startActivity(Intent(this, AuthActivity::class.java))
        finish()
    }

    fun showLoading() {
        loadingUtil.showLoading()
    }

    fun hideLoading() {
        loadingUtil.hideLoading()
    }

    fun setTitle(title: String) {
        binding.toolbar.title.text = title
    }

    fun back() {

    }

    fun showBottomNav(isVisible: Boolean) {
        binding.bottomNavView.isVisible = isVisible
        binding.toolbar.ivBack.isVisible = !isVisible
    }
}