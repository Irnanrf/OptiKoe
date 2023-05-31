package com.irnanrf.optikoecapstone

import android.opengl.Visibility
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.*
import com.irnanrf.optikoecapstone.adapter.ProductAdapter
import com.irnanrf.optikoecapstone.adapter.TransactionHistoryAdapter
import com.irnanrf.optikoecapstone.data.DummyData
import com.irnanrf.optikoecapstone.data.model.Product
import com.irnanrf.optikoecapstone.data.model.TransactionHistory
import com.irnanrf.optikoecapstone.databinding.ActivityTransactionHistoryBinding

class TransactionHistoryActivity : AppCompatActivity() {

    private lateinit var binding : ActivityTransactionHistoryBinding
    private val adapterTransactionHistory = TransactionHistoryAdapter()

    lateinit var listHistory : MutableList<TransactionHistory>

    var firebaseAuth = FirebaseAuth.getInstance()
    val firestore = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTransactionHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setTitle("Transaction History")

        listHistory = DummyData.listHistory.toMutableList()
        listHistory.clear()
        //adapterTransactionHistory.addItems(listHistory)
        binding.rvTransactionHistory.adapter = adapterTransactionHistory

        getTransactionHistory()
    }

    private fun getTransactionHistory(){
        val userId = firebaseAuth.currentUser?.uid
        firestore.collection("dataTransaction").whereEqualTo("idUser", userId)
            .addSnapshotListener(EventListener { value, error ->
                if (error != null) {
                    Log.e("Firestore Error", error.message!!)
                    return@EventListener
                }
                for (dc in value!!.documentChanges) {
                    Log.d("firestore", "DocumentSnapshot data: ${dc.document}")
                    if (dc.type == DocumentChange.Type.ADDED) {
                        var productName: String
                        var productPrice: String
                        var quantity: String
                        var shippingAddress: String
                        var shippingCourier: String
                        var shippingCost: String
                        var shippingName: String
                        var shippingPhone: String
                        var totalPrice: String
                        var paymentMethod: String
                        var status: String
                        var date: String

                        productName = dc.document["productName"].toString()
                        productPrice = dc.document["productPrice"].toString()
                        quantity = dc.document["quantity"].toString()
                        shippingAddress = dc.document["shippingAddress"].toString()
                        shippingCourier = dc.document["shippingCourier"].toString()
                        shippingCost = dc.document["shippingCost"].toString()
                        shippingName = dc.document["shippingName"].toString()
                        shippingPhone = dc.document["shippingPhone"].toString()
                        totalPrice = dc.document["totalPrice"].toString()
                        paymentMethod = dc.document["paymentMethod"].toString()
                        status = dc.document["status"].toString()
                        date = dc.document["date"].toString()
                        listHistory.add(
                            TransactionHistory(
                                userId,
                                productName,
                                productPrice,
                                quantity,
                                shippingAddress,
                                shippingCourier,
                                shippingCost,
                                shippingName,
                                shippingPhone,
                                totalPrice,
                                paymentMethod,
                                status,
                                date,
                                R.drawable.frame1
                            )
                        )
                    }
                    println(dc.type)
                    adapterTransactionHistory.addItems(listHistory)

                    if(listHistory.isEmpty()){
                        binding.txtLabelTransaction.visibility = View.VISIBLE
                    } else{
                        binding.txtLabelTransaction.visibility = View.INVISIBLE
                    }
                }
            })
    }
}