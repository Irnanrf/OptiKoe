package com.irnanrf.optikoecapstone

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.irnanrf.optikoecapstone.databinding.ActivityTransactionBinding
import com.irnanrf.optikoecapstone.util.toRupiah
import koleton.api.hideSkeleton
import koleton.api.loadSkeleton
import java.text.SimpleDateFormat
import java.util.*

class TransactionActivity : AppCompatActivity() {

    private lateinit var binding : ActivityTransactionBinding

    var firebaseAuth = FirebaseAuth.getInstance()
    var mDatabase = FirebaseDatabase.getInstance().getReference("users")
    val firestore = FirebaseFirestore.getInstance()

    var price = "0"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTransactionBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setTitle("Transaction")
        binding.lindetails.loadSkeleton()

        setupAction()

        getIntentData()
        getUserAddress()

    }

    private fun setupAction(){
        binding.btnOrder.setOnClickListener{
            addTransaction()
        }
    }


    private fun getIntentData(){
        var quantity = intent.getStringExtra("productQuantity").toString()
        price = intent.getStringExtra("productPrice").toString()
        price = price.replace("Rp","")
        price = price.replace(".","")
        val quantityInt = Integer.valueOf(quantity)
        val priceInt = Integer.valueOf(price)
        var totalPrice = quantityInt * priceInt
        binding.tvProductName.text = intent.getStringExtra("productName").toString()
        binding.tvQuantity.text = quantity + " x " + price
        binding.tvTotalproductprice.text = totalPrice.toString().toRupiah()
        binding.tvTotalproduct.text = totalPrice.toString().toRupiah()
        binding.tvShippingcost.text = (10000).toRupiah()
        binding.tvTotalpayment.text = (totalPrice + 10000).toRupiah()

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
                binding.lindetails.hideSkeleton()
                binding.progressTransaction.visibility = View.INVISIBLE
            }.addOnFailureListener{
                Log.e("firebase", "Error getting data", it)
            }
        }
    }

    private fun addTransaction(){

        val time = Calendar.getInstance().time
        val formatter = SimpleDateFormat("d MMM yyyy HH:mm")
        val current = formatter.format(time)
        val dataTransaction: MutableMap<String, Any> = HashMap()

        val idUser: String = firebaseAuth.currentUser?.uid.toString()
        val date = Timestamp.now()
        val productName: String = binding.tvProductName.text.toString()
        val productPrice: String = price
        val productQuantity: String = intent.getStringExtra("productQuantity").toString()
        val shippingName: String = binding.tvName.text.toString()
        val shippingAddress: String = binding.tvAddress.text.toString()
        val shippingPhone: String = binding.tvPhone.text.toString()
        val shippingCourier: String = "Recommended Courier"
        val shippingCost: String = 10000.toString()
        val status: String = "On Process"
        val paymentMethod: String = "Bank Transfer"
        var totalPrice = binding.tvTotalpayment.text.toString().replace("Rp","").replace(".","")

        dataTransaction["idUser"] = idUser
        dataTransaction["date"] = date
        dataTransaction["productName"] = productName
        dataTransaction["productPrice"] = productPrice
        dataTransaction["productQuantity"] = productQuantity
        dataTransaction["shippingName"] = shippingName
        dataTransaction["shippingAddress"] = shippingAddress
        dataTransaction["shippingPhone"] = shippingPhone
        dataTransaction["shippingCourier"] = shippingCourier
        dataTransaction["shippingCost"] = shippingCost
        dataTransaction["status"] = status
        dataTransaction["paymentMethod"] = paymentMethod
        dataTransaction["totalPrice"] = totalPrice

        firestore.collection("dataTransaction")
            .add(dataTransaction).addOnSuccessListener(OnSuccessListener<DocumentReference?> {
                Toast.makeText(applicationContext, "Order Success", Toast.LENGTH_SHORT).show()
                val i = Intent(this, TransactionHistoryActivity::class.java)
                startActivity(i)
            }).addOnFailureListener(OnFailureListener { })

    }

}