package com.irnanrf.optikoecapstone

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.irnanrf.optikoecapstone.databinding.ActivityTryOnBinding

class TryOnActivity : AppCompatActivity() {

    private lateinit var binding : ActivityTryOnBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTryOnBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}