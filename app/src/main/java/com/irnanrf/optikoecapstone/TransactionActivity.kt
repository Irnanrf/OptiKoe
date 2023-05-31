package com.irnanrf.optikoecapstone

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.irnanrf.optikoecapstone.databinding.ActivityTransactionBinding
import com.irnanrf.optikoecapstone.util.toRupiah
import koleton.api.hideSkeleton

class TransactionActivity : AppCompatActivity() {

    private lateinit var binding : ActivityTransactionBinding

    var firebaseAuth = FirebaseAuth.getInstance()
    var mDatabase = FirebaseDatabase.getInstance().getReference("users")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTransactionBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setTitle("Transaction")

        getIntentData()
        getUserAddress()

    }

    private fun getIntentData(){
        var quantity = intent.getStringExtra("productQuantity").toString()
        var price = intent.getStringExtra("productPrice").toString()
        //var totalPrice = Integer.valueOf(quantity) * Integer.valueOf(price)
        binding.tvProductName.text = intent.getStringExtra("productName").toString()
        binding.tvQuantity.text = quantity + " x " + price

    }

    private fun getUserAddress(){
        val userId = firebaseAuth.currentUser?.uid
        if (userId != null) {
            mDatabase.child(userId).get().addOnSuccessListener {
                Log.i("firebase", "Got value ${it.value}")
                //Load Data Address
                binding.tvName.text = it.child("name").value.toString()
                binding.tvPhone.text = it.child("phone").value.toString()
                binding.tvAddress.text = it.child("address").value.toString()
            }.addOnFailureListener{
                Log.e("firebase", "Error getting data", it)
            }
        }
    }
}