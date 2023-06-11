package com.irnanrf.optikoecapstone

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.irnanrf.optikoecapstone.adapter.ProductAdapter
import com.irnanrf.optikoecapstone.data.DummyData.listProduct
import com.irnanrf.optikoecapstone.data.model.Product
import com.irnanrf.optikoecapstone.data.model.TransactionHistory
import com.irnanrf.optikoecapstone.databinding.ActivityHomeBinding
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class HomeActivity : AppCompatActivity() {

    private lateinit var binding : ActivityHomeBinding

    var firebaseAuth = FirebaseAuth.getInstance()
    val firestore = FirebaseFirestore.getInstance()

    private val adapterProduct = ProductAdapter()

    lateinit var productList : MutableList<Product>
    lateinit var tempList : MutableList<Product>

    override fun onStart(){
        super.onStart()
        if(firebaseAuth.currentUser==null){
            val i = Intent(this@HomeActivity, LandingActivity::class.java)
            startActivity(i)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        productList = listProduct.toMutableList()
        productList.clear()
        getProducts()

        tempList = productList.toMutableList()
        adapterProduct.addItems(tempList)
        binding.rvProductTerbaru.adapter = adapterProduct

        val time = Calendar.getInstance().time
        val formatter = SimpleDateFormat("d MMM yyyy HH:mm")
        val current = formatter.format(time)
        Log.d("Time", current)

        setupAction()


    }

    override fun onBackPressed() {
        if (firebaseAuth.currentUser==null){
            val i = Intent(this@HomeActivity, LandingActivity::class.java)
            startActivity(i)
        } else{
            val mainActivity = Intent(Intent.ACTION_MAIN)
            mainActivity.addCategory(Intent.CATEGORY_HOME)
            mainActivity.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(mainActivity)
            finish()
        }
        return
    }

    private fun setupAction() {
        binding.linProfile.setOnClickListener {
            val i = Intent(applicationContext, ProfileActivity::class.java)
            startActivity(i)
        }

        binding.linHistory.setOnClickListener {
            val i = Intent(applicationContext, TransactionHistoryActivity::class.java)
            startActivity(i)
        }

        binding.linFavorite.setOnClickListener {
            val i = Intent(applicationContext, WishlistActivity::class.java)
            startActivity(i)
        }

        binding.linCamera.setOnClickListener {
            val i = Intent(applicationContext, ScanFaceActivity::class.java)
            startActivity(i)
        }

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                TODO("Not yet implemented")

            }

            override fun onQueryTextChange(query: String?): Boolean {
                tempList.clear()
                adapterProduct.removeItems()
                val searchText = query!!.lowercase()
                if(searchText.isNotEmpty()){
                    productList.forEach{
                        if(it.name?.lowercase()?.contains(searchText) == true){
                            tempList.add(it)
                        }
                    }
                    adapterProduct.addItems(tempList)
                }else{
                    tempList.clear()
                    adapterProduct.removeItems()
                    tempList.addAll(productList)
                    adapterProduct.addItems(tempList)
                }
                return false
            }
        })

        setupChipGroup()

    }

    private fun setupChipGroup(){
        binding.chipGroup.setOnCheckedStateChangeListener{ group, checkedId ->
            var valueChip = ""
            if(binding.chipRound.isChecked){
                valueChip = "Round"
            } else if(binding.chipOval.isChecked){
                valueChip = "Oval"
            } else if(binding.chipSquare.isChecked){
                valueChip = "Square"
            } else if(binding.chipHeart.isChecked){
                valueChip = "Heart"
            } else if(binding.chipRect.isChecked){
                valueChip = "Rectangle"
            }else if(binding.chipTryOn.isChecked){
                valueChip = "Try-On"
            }

            filterByChips(valueChip)
        }

    }

    private fun filterByChips(query : String){
        tempList.clear()
        adapterProduct.removeItems()
        val searchText = query!!.lowercase()
        if(searchText.isNotEmpty()){
            productList.forEach{
                if(it.faceshape?.lowercase()?.contains(searchText)  == true){
                    tempList.add(it)
                }
            }
            adapterProduct.addItems(tempList)
        }else{
            tempList.clear()
            adapterProduct.removeItems()
            tempList.addAll(productList)
            adapterProduct.addItems(tempList)
        }
    }

    private fun getProducts(){
        firestore.collection("dataProduct")
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

                        productList.add(
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
                    }
                    println(dc.type)
                    adapterProduct.removeItems()
                    adapterProduct.addItems(productList)
                }
            })
    }
}