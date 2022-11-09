package com.tawajood.vetclinic.ui.splash

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.bumptech.glide.Glide
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.maps.model.LatLng
import com.tawajood.vetclinic.R
import com.tawajood.vetclinic.databinding.ActivitySplashBinding
import com.tawajood.vetclinic.ui.auth.AuthActivity
import com.tawajood.vetclinic.ui.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint

@SuppressLint("CustomSplashScreen")
@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {
    private var lat: Double? = null
    private var lng: Double? = null
    private lateinit var mFusedLocationClient: FusedLocationProviderClient
    private lateinit var currentLatLng: LatLng

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)




        Glide.with(this)
            .load(R.drawable.logo)
            .into(binding.logo)

        Handler(Looper.myLooper()!!).postDelayed({

            if (PrefsHelper.isFirst()) {
                startActivity(Intent(this, AuthActivity::class.java))
            } else {
                if (PrefsHelper.getToken().isEmpty()) {
                    startActivity(Intent(this, AuthActivity::class.java))
                } else {
                    startActivity(Intent(this, MainActivity::class.java))
                }
            }


            finish()
        }, 2000)
    }


}