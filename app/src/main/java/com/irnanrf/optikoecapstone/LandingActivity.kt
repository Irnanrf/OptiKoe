package com.irnanrf.optikoecapstone

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.irnanrf.optikoecapstone.databinding.ActivityLandingBinding

class LandingActivity : AppCompatActivity() {

    private lateinit var binding : ActivityLandingBinding

    var firebaseAuth = FirebaseAuth.getInstance()

    override fun onStart(){
        super.onStart()
        if(firebaseAuth.currentUser!=null){
            val i = Intent(this@LandingActivity, HomeActivity::class.java)
            startActivity(i)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLandingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        setupAction()
    }

    private fun setupAction() {
        binding.btnLogin.setOnClickListener {
            val i = Intent(this@LandingActivity, LoginActivity::class.java)
            startActivity(i)
        }

        binding.btnRegister.setOnClickListener {
            val i = Intent(this@LandingActivity, RegisterActivity::class.java)
            startActivity(i)
        }

    }

    override fun onBackPressed() {
        val mainActivity = Intent(Intent.ACTION_MAIN)
        mainActivity.addCategory(Intent.CATEGORY_HOME)
        mainActivity.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(mainActivity)
        finish()
    }
}