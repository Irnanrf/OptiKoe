package com.irnanrf.optikoecapstone

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.FieldPath
import com.google.firebase.firestore.FirebaseFirestore
import com.irnanrf.optikoecapstone.adapter.WishlistAdapter
import com.irnanrf.optikoecapstone.data.DummyData
import com.irnanrf.optikoecapstone.data.model.Product
import com.irnanrf.optikoecapstone.databinding.ActivityWishlistBinding

class WishlistActivity : AppCompatActivity() {

    private lateinit var binding : ActivityWishlistBinding

    private val adapterWishlist = WishlistAdapter()
    lateinit var wishlistList : MutableList<Product>

    var firebaseAuth = FirebaseAuth.getInstance()
    val firestore = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWishlistBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setTitle("Wishlist")
        Log.d("lifecycle","onCreate invoked");

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }


    override fun onResume() {
        super.onResume()
        Log.d("lifecycle","onResume invoked");
        wishlistList = DummyData.listProduct.toMutableList()
        wishlistList.clear()
        adapterWishlist.addItems(wishlistList)
        adapterWishlist.removeItems()
        binding.rvWishlist.adapter = adapterWishlist
        getWishlist()
    }




    private fun getWishlist(){
        val userId = firebaseAuth.currentUser?.uid
        firestore.collection("dataWishlist").whereEqualTo("idUser", userId)
            .addSnapshotListener(EventListener { value, error ->
                if (error != null) {
                    Log.e("Firestore Error", error.message!!)
                    return@EventListener
                }
                for (dc in value!!.documentChanges) {
                    Log.d("firestore", "DocumentSnapshot data: ${dc.document}")
                    if (dc.type == DocumentChange.Type.ADDED) {

                        var productId = dc.document["productId"].toString()
                        var isFavorite = dc.document["isFavorite"].toString()

                        if(isFavorite.equals("1")){
                            // get Product by product ID
                            firestore.collection("dataProduct").whereEqualTo(FieldPath.documentId(), productId)
                                .addSnapshotListener(EventListener { value, error ->
                                    if (error != null) {
                                        Log.e("Firestore Error", error.message!!)
                                        return@EventListener
                                    }
                                    for (dc in value!!.documentChanges) {
                                        Log.d("firestore", "DocumentSnapshot data: ${dc.document}")
                                        if (dc.type == DocumentChange.Type.ADDED) {
                                            var productId: String
                                            var productName: String
                                            var productPrice: String
                                            var productDesc: String
                                            var faceShape: String
                                            var productLocation: String
                                            var imagePath: String

                                            productId = dc.document.id
                                            productName = dc.document["productName"].toString()
                                            productPrice = dc.document["productPrice"].toString()
                                            productDesc = dc.document["productDesc"].toString()
                                            faceShape = dc.document["faceShape"].toString()
                                            productLocation = dc.document["productLocation"].toString()
                                            imagePath = dc.document["imagePath"].toString()

                                            wishlistList.add(
                                                Product(
                                                    productId,
                                                    productName,
                                                    Integer.parseInt(productPrice),
                                                    productDesc,
                                                    faceShape,
                                                    productLocation,
                                                    imagePath
                                                )
                                            )
                                            print(wishlistList)
                                        }
                                        println(dc.type)
                                        adapterWishlist.removeItems()
                                        adapterWishlist.addItems(wishlistList)
                                    }
                                })
                        }


                    }

                }
            })
    }
}