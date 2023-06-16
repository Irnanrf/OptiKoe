package com.irnanrf.optikoecapstone

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.bumptech.glide.Glide
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.irnanrf.optikoecapstone.data.model.Product
import com.irnanrf.optikoecapstone.databinding.ActivityDetailProductBinding
import com.irnanrf.optikoecapstone.util.toRupiah
import java.util.HashMap

class DetailProductActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailProductBinding

    var firebaseAuth = FirebaseAuth.getInstance()
    val firestore = FirebaseFirestore.getInstance()

    var isFavorite = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailProductBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        var desc = intent.getStringExtra("product_desc").toString().replace("\\n", "\n")

        Log.d("Test", intent.getStringExtra("product_id").toString())
        binding.tvNameProduct.text = intent.getStringExtra("product_name").toString()
        binding.tvPrice.text = intent.getStringExtra("product_price").toString().toRupiah()
        binding.tvDescription.text = desc
        binding.tvFaceshape.text = intent.getStringExtra("product_face").toString()
        binding.tvLocation.text = intent.getStringExtra("product_location").toString()
        Glide.with(this)
            .load(intent.getStringExtra("product_image"))
            .into(binding.imageView2)

        setupAction()
        getFavoriteStatus()
        checkTryon()
    }

    private fun checkTryon(){
        var faceShape = binding.tvFaceshape.text.toString()
        if(faceShape.contains("Try-On") == true){
            binding.imgTry.visibility = View.VISIBLE
        } else{
            binding.imgTry.visibility = View.INVISIBLE
        }
    }

    private fun yesFavoriteStatus(){
        val userId = firebaseAuth.currentUser?.uid
        val productId = intent.getStringExtra("product_id").toString()

        firestore.collection("dataWishlist").whereEqualTo("idUser", userId).whereEqualTo("productId", productId)
            .addSnapshotListener(EventListener { value, error ->
                if (error != null) {
                    Log.e("Firestore Error", error.message!!)
                    return@EventListener
                }

                for (dc in value!!.documentChanges) {
                    Log.d("firestore", "DocumentSnapshot data: ${dc.document}")
                    if (dc.type == DocumentChange.Type.ADDED) {
                        firestore.collection("dataWishlist").document(dc.document.id).update(mapOf(
                            "idUser" to userId,
                            "isFavorite" to "1",
                            "productId" to productId
                        )
                        )
                    }
                }
            })
        Toast.makeText(applicationContext, "Add to Wishlist", Toast.LENGTH_SHORT).show()
    }

    private fun noFavoriteStatus(){
        val userId = firebaseAuth.currentUser?.uid
        val productId = intent.getStringExtra("product_id").toString()

        firestore.collection("dataWishlist").whereEqualTo("idUser", userId).whereEqualTo("productId", productId)
            .addSnapshotListener(EventListener { value, error ->
                if (error != null) {
                    Log.e("Firestore Error", error.message!!)
                    return@EventListener
                }

                for (dc in value!!.documentChanges) {
                    Log.d("firestore", "DocumentSnapshot data: ${dc.document}")
                    if (dc.type == DocumentChange.Type.ADDED) {
                        firestore.collection("dataWishlist").document(dc.document.id).update(mapOf(
                            "idUser" to userId,
                            "isFavorite" to "0",
                            "productId" to productId
                        )
                        )
                    }
                }
            })
        Toast.makeText(applicationContext, "Remove from Wishlist", Toast.LENGTH_SHORT).show()
    }

    private fun addFavorite(){
        val dataWishlist: MutableMap<String, Any> = HashMap()

        dataWishlist["idUser"] = firebaseAuth.currentUser?.uid.toString()
        dataWishlist["isFavorite"] = "1"
        dataWishlist["productId"] = intent.getStringExtra("product_id").toString()

        firestore.collection("dataWishlist")
            .add(dataWishlist).addOnSuccessListener(OnSuccessListener<DocumentReference?> {
                Toast.makeText(applicationContext, "Add to Wishlist", Toast.LENGTH_SHORT).show()
            }).addOnFailureListener(OnFailureListener { })

        getFavoriteStatus()
    }

    private fun getFavoriteStatus(){
        val userId = firebaseAuth.currentUser?.uid
        val productId = intent.getStringExtra("product_id").toString()

        firestore.collection("dataWishlist").whereEqualTo("idUser", userId).whereEqualTo("productId", productId)
            .addSnapshotListener(EventListener { value, error ->
                if (error != null) {
                    Log.e("Firestore Error", error.message!!)
                    return@EventListener
                }
                for (dc in value!!.documentChanges) {
                    Log.d("firestore", "DocumentSnapshot data: ${dc.document}")
                    isFavorite = dc.document["isFavorite"].toString()
                    if(isFavorite.equals("1")){
                        binding.imgFav.setImageResource(R.drawable.ic_baseline_favorite_24)
                    }else{
                        binding.imgFav.setImageResource(R.drawable.ic_baseline_favorite_border_24)
                    }

                    if (dc.type == DocumentChange.Type.ADDED) {

                    }
                }
            })

    }



    private fun setupAction(){
        binding.imgFav.setOnClickListener{

            println("imgFav Clicked")
            if(isFavorite.equals("1")){
                noFavoriteStatus()
            } else if (isFavorite.equals("0")){
                yesFavoriteStatus()
            } else if (isFavorite.equals("")){
                addFavorite()
            }

        }

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
            var imgPath = intent.getStringExtra("product_image").toString()
            i.putExtra("productImage", imgPath)
            startActivity(i)
        }

        binding.imgTry.setOnClickListener{
            val i = Intent(this@DetailProductActivity, TryOnActivity::class.java)
            startActivity(i)
        }

    }
}