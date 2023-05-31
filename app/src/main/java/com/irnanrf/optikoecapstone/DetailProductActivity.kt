package com.irnanrf.optikoecapstone

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.irnanrf.optikoecapstone.databinding.ActivityDetailProductBinding
import com.irnanrf.optikoecapstone.util.toRupiah

class DetailProductActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailProductBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailProductBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        binding.tvNameProduct.text = intent.getStringExtra("product_name").toString()
        binding.tvPrice.text = intent.getStringExtra("product_price").toString().toRupiah()
        binding.tvDescription.text = intent.getStringExtra("product_desc").toString()
        binding.tvFaceshape.text = intent.getStringExtra("product_face").toString()
        binding.tvLocation.text = intent.getStringExtra("product_location").toString()
        binding.imageView2.setImageResource(Integer.parseInt(intent.getStringExtra("product_image")))

        setupAction()
    }

    private fun setupAction(){
        binding.imgMinus.setOnClickListener{
            var valueQuantity = Integer.valueOf(binding.tvQuantity.text.toString())
            if(valueQuantity == 0){
                binding.tvQuantity.text = valueQuantity.toString()
            }else{
                valueQuantity -= 1
                binding.tvQuantity.text = valueQuantity.toString()
            }
        }

        binding.imgPlus.setOnClickListener{
            var valueQuantity = Integer.valueOf(binding.tvQuantity.text.toString())
            if(valueQuantity == 99){
                binding.tvQuantity.text = valueQuantity.toString()
            }else{
                valueQuantity += 1
                binding.tvQuantity.text = valueQuantity.toString()
            }
        }

        binding.btnBuyNow.setOnClickListener{
            val i = Intent(this@DetailProductActivity, TransactionActivity::class.java)
            i.putExtra("productName", binding.tvNameProduct.text.toString())
            i.putExtra("productPrice", binding.tvPrice.text.toString())
            i.putExtra("productQuantity", binding.tvQuantity.text.toString())
            startActivity(i)
        }


    }
}