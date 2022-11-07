package com.tawajood.vetclinic.ui.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.navigation.NavController
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
    private lateinit var header: View


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        loadingUtil = LoadingUtil(this)
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
}