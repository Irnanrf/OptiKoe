package com.irnanrf.optikoecapstone

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.irnanrf.optikoecapstone.databinding.ActivityProfileBinding
import koleton.api.hideSkeleton
import koleton.api.loadSkeleton

class ProfileActivity : AppCompatActivity() {

    private lateinit var binding : ActivityProfileBinding

    var firebaseAuth = FirebaseAuth.getInstance()
    var database = FirebaseDatabase.getInstance()
    var mDatabase = database.getReference("users")
    var mDatabaseTrans = database.getReference("transaction")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //supportActionBar?.hide()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setTitle("Profile")

        //show skeleton
        binding.linearProfile.loadSkeleton()
        binding.linearAddress.loadSkeleton()


        val userId = firebaseAuth.currentUser?.uid

        if (userId != null) {
            mDatabase.child(userId).get().addOnSuccessListener {
                Log.i("firebase", "Got value ${it.value}")
                binding.txtName.text = it.child("name").value.toString()
                binding.txtPhone.text = it.child("phone").value.toString()
                Glide.with(this)
                    .load(it.child("imgpath").value.toString())
                    .into(binding.imgProfile)
                binding.txtEmail.text = firebaseAuth.currentUser?.email
                binding.progressProfile.visibility = View.INVISIBLE

                //Load Data Address
                binding.txtNameAddress.text = it.child("name").value.toString()
                binding.txtPhoneAddress.text = it.child("phone").value.toString()
                binding.txtAddress.text = it.child("address").value.toString()
                //hide skeleton
                binding.linearProfile.hideSkeleton()
                binding.linearAddress.hideSkeleton()
            }.addOnFailureListener{
                Log.e("firebase", "Error getting data", it)
            }
        }

        setupAction()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun setupAction() {

        binding.imgSetting.setOnClickListener {
            val i = Intent(applicationContext, EditProfileActivity::class.java)
            startActivity(i)
        }

        binding.btnLogout.setOnClickListener {
            logOutDialog()
        }

    }

    override fun onBackPressed() {
        val i = Intent(this@ProfileActivity, HomeActivity::class.java)
        startActivity(i)
        return
    }


    private fun logOutDialog(){
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Log Out")
        builder.setMessage("Are you sure want to Log Out?")

        builder.setPositiveButton(android.R.string.yes) { _, _ ->
            firebaseAuth.signOut()
            Toast.makeText(applicationContext,
                "Log Out Success", Toast.LENGTH_SHORT).show()
            val i = Intent(applicationContext, HomeActivity::class.java)
            startActivity(i)
        }

        builder.setNegativeButton(android.R.string.no) { _, _ ->
            Toast.makeText(applicationContext,
                android.R.string.no, Toast.LENGTH_SHORT).show()
        }

        builder.show()
    }
}