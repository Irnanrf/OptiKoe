package com.irnanrf.optikoecapstone

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.irnanrf.optikoecapstone.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding : ActivityRegisterBinding

    var firebaseAuth = FirebaseAuth.getInstance()
    var database = FirebaseDatabase.getInstance()
    var mDatabase = database.getReference("users")

    override fun onStart(){
        super.onStart()
        if(firebaseAuth.currentUser!=null){
            val i = Intent(this@RegisterActivity, HomeActivity::class.java)
            startActivity(i)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        setupAction()
    }

    override fun onBackPressed() {
        val i = Intent(this@RegisterActivity, LandingActivity::class.java)
        startActivity(i)
        return
    }

    private fun setupAction() {
        binding.btnRegister.setOnClickListener {
            //Check valid
            if(binding.txtEmail.text?.isNotEmpty() == true && binding.txtPassword.text?.isNotEmpty() == true && binding.txtConfirmPassword.text?.isNotEmpty() == true) {
                if(binding.txtPassword.text.toString().equals(binding.txtConfirmPassword.text.toString())){
                    // Move to Register User Data
                    var userEmail = binding.txtEmail.text.toString()
                    var userPassword = binding.txtPassword.text.toString()
                    val i = Intent(this@RegisterActivity, RegisterUserDataActivity::class.java)
                    i.putExtra("emailUser",userEmail)
                    i.putExtra("passwordUser",userPassword)
                    startActivity(i)
                } else{
                    Toast.makeText(this, "Confirmation Password Must Be Same!", LENGTH_SHORT).show()
                    binding.progressRegister.visibility = View.INVISIBLE
                }
            } else{
                Toast.makeText(this, "Please check your input!", LENGTH_SHORT).show()
                binding.progressRegister.visibility = View.INVISIBLE
            }

//            binding.progressRegister.visibility = View.VISIBLE
//            //check valid
//            if(binding.txtEmail.text?.isNotEmpty() == true && binding.txtPassword.text?.isNotEmpty() == true && binding.txtConfirmPassword.text?.isNotEmpty() == true) {
//                if(binding.txtPassword.text.toString().equals(binding.txtConfirmPassword.text.toString())){
//                    // Launch Register
//                    processRegister()
//                } else{
//                    Toast.makeText(this, "Confirmation Password Must Be Same!", LENGTH_SHORT).show()
//                    binding.progressRegister.visibility = View.INVISIBLE
//                }
//            } else{
//                Toast.makeText(this, "Check Your Input!", LENGTH_SHORT).show()
//                binding.progressRegister.visibility = View.INVISIBLE
//            }
        }
    }


}