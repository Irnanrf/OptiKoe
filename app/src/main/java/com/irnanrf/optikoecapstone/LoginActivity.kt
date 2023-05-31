package com.irnanrf.optikoecapstone

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.irnanrf.optikoecapstone.databinding.ActivityLoginBinding
import com.irnanrf.optikoecapstone.databinding.ActivityRegisterBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var binding : ActivityLoginBinding
    var firebaseAuth = FirebaseAuth.getInstance()

    override fun onStart(){
        super.onStart()
        if(firebaseAuth.currentUser!=null){
            val i = Intent(this@LoginActivity, HomeActivity::class.java)
            startActivity(i)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        setupAction()
    }

    override fun onBackPressed() {
        val i = Intent(this@LoginActivity, LandingActivity::class.java)
        startActivity(i)
        return
    }

    private fun setupAction() {
        binding.btnLogin.setOnClickListener {
            //check valid
            binding.progressLogin.visibility = View.VISIBLE
            if(binding.txtEmail.text?.isNotEmpty() == true && binding.txtPassword.text?.isNotEmpty() == true) {
                processLogin()
            } else{
                Toast.makeText(this, "Check Your Input!", Toast.LENGTH_SHORT).show()
                binding.progressLogin.visibility = View.INVISIBLE
            }

        }
    }

    private fun processLogin(){
        val email = binding.txtEmail.text.toString()
        val password = binding.txtPassword.text.toString()

        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener{ task ->
            if(task.isSuccessful){
                val i = Intent(this@LoginActivity, HomeActivity::class.java)
                startActivity(i)
                finish()
                binding.progressLogin.visibility = View.INVISIBLE
            }else{

            }
        }.addOnFailureListener{ error ->
            Toast.makeText(this@LoginActivity, "Check your email or password!", Toast.LENGTH_SHORT).show()
            binding.progressLogin.visibility = View.INVISIBLE
        }
    }
}