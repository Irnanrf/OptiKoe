package com.irnanrf.optikoecapstone

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.irnanrf.optikoecapstone.data.model.User
import com.irnanrf.optikoecapstone.databinding.ActivityRegisterUserDataBinding

class RegisterUserDataActivity : AppCompatActivity() {

    private lateinit var binding : ActivityRegisterUserDataBinding

    var firebaseAuth = FirebaseAuth.getInstance()
    var database = FirebaseDatabase.getInstance()
    var mDatabase = database.getReference("users")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterUserDataBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        setupAction()
    }

    private fun setupAction(){
        binding.btnRegister.setOnClickListener{
            //Check Valid
            if(binding.txtName.text?.isNotEmpty() == true && binding.txtPhone.text?.isNotEmpty() == true && binding.txtAddress.text?.isNotEmpty() == true) {
                processRegister()
                binding.progressRegisterData.visibility = View.VISIBLE
            } else{
                Toast.makeText(this@RegisterUserDataActivity, "Check Your Input!",
                    Toast.LENGTH_SHORT
                ).show()
            }



        }
    }

    private fun processRegister(){
        //Get Intent From Register Page
        val bundle = intent.extras
        val emailUser = bundle!!.getString("emailUser")
        val passwordUser = bundle!!.getString("emailUser")

        val nameUser = binding.txtName.text.toString()
        val phoneUser = binding.txtPhone.text.toString()
        val addressUser = binding.txtAddress.text.toString()

        if(emailUser != null && passwordUser != null){
            firebaseAuth.createUserWithEmailAndPassword(emailUser, passwordUser).addOnCompleteListener{ task ->
                if(task.isSuccessful){
                    val userId = firebaseAuth.currentUser?.uid
                    val defaultImg = "https://firebasestorage.googleapis.com/v0/b/optiqoe.appspot.com/o/userprofile%2Fdefault.png?alt=media&token=5053d450-1c45-4f9e-b183-dd5c83fa685d"
                    val user = User(nameUser,phoneUser,addressUser,defaultImg)
                    if(userId != null){
                        mDatabase.child(userId).setValue(user).addOnCompleteListener {
                            Toast.makeText(this@RegisterUserDataActivity, "Input User Data Success",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                    Toast.makeText(this@RegisterUserDataActivity, "Register Success", Toast.LENGTH_SHORT).show()
                    val i = Intent(this@RegisterUserDataActivity, LoginActivity::class.java)
                    startActivity(i)
                    finish()
                    binding.progressRegisterData.visibility = View.INVISIBLE
                }else{

                }
            }.addOnFailureListener{ error ->
                Toast.makeText(this@RegisterUserDataActivity, "Register Failed", Toast.LENGTH_SHORT).show()
                binding.progressRegisterData.visibility = View.INVISIBLE
            }
        }


    }
}