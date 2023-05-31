package com.irnanrf.optikoecapstone

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.irnanrf.optikoecapstone.databinding.ActivityDetailProductBinding
import com.irnanrf.optikoecapstone.databinding.ActivityDetailTransactionBinding
import com.irnanrf.optikoecapstone.util.toRupiah

class DetailTransactionActivity : AppCompatActivity() {

    private lateinit var binding : ActivityDetailTransactionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailTransactionBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setTitle("Details Transaction")

        setData()
    }

    private fun setData(){
        binding.tvDate.text = intent.getStringExtra("date").toString()
        binding.tvProductName.text = intent.getStringExtra("productName").toString()
        var quantity = Integer.parseInt(intent.getStringExtra("quantity").toString())
        var productPrice = Integer.parseInt(intent.getStringExtra("productPrice").toString())
        var totalProductPrice = quantity * productPrice
        binding.tvQuantity.text =  quantity.toString() + " x " + productPrice.toRupiah()
        binding.tvTotalproductprice.text = totalProductPrice.toRupiah()
        binding.tvCourier.text = intent.getStringExtra("shippingCourier").toString()
        binding.tvName.text = intent.getStringExtra("shippingName").toString()
        binding.tvPhone.text = intent.getStringExtra("shippingPhone").toString()
        binding.tvAddress.text = intent.getStringExtra("shippingAddress").toString()
        binding.tvMethod.text = intent.getStringExtra("paymentMethod").toString()
        binding.tvTotalproduct.text = totalProductPrice.toRupiah()
        binding.tvShippingcost.text = intent.getStringExtra("shippingCost").toString().toRupiah()
        binding.tvTotalpayment.text = intent.getStringExtra("totalPrice").toString().toRupiah()
        binding.tvStatus.text = intent.getStringExtra("status").toString()
        binding.imgProduct.setImageResource(Integer.parseInt(intent.getStringExtra("image").toString()))
    }
}