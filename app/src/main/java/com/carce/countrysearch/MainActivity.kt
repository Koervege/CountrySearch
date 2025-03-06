package com.carce.countrysearch

import android.os.Bundle
import androidx.activity.SystemBarStyle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import com.carce.countrysearch.databinding.ActivityMainBinding
import com.carce.countrysearch.viewmodel.CountryViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: CountryViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge(SystemBarStyle.dark(
            getColor(R.color.black)
        ))
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this).get()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}
