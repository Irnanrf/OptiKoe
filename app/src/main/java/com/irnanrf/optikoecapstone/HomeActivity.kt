package com.irnanrf.optikoecapstone

import android.content.Intent
import android.os.Bundle
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.irnanrf.optikoecapstone.adapter.ProductAdapter
import com.irnanrf.optikoecapstone.data.DummyData.listProduct
import com.irnanrf.optikoecapstone.data.model.Product
import com.irnanrf.optikoecapstone.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {

    private lateinit var binding : ActivityHomeBinding

    var firebaseAuth = FirebaseAuth.getInstance()

    private val adapterProduct = ProductAdapter()

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

        tempList = listProduct.toMutableList()
        adapterProduct.addItems(tempList)
        binding.rvProductTerbaru.adapter = adapterProduct

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

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                TODO("Not yet implemented")

            }

            override fun onQueryTextChange(query: String?): Boolean {
                tempList.clear()
                adapterProduct.removeItems()
                val searchText = query!!.lowercase()
                if(searchText.isNotEmpty()){
                    listProduct.forEach{
                        if(it.name?.lowercase()?.contains(searchText) == true){
                            tempList.add(it)
                        }
                    }
                    adapterProduct.addItems(tempList)
                }else{
                    tempList.clear()
                    adapterProduct.removeItems()
                    tempList.addAll(listProduct)
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
            }

            filterByChips(valueChip)
        }

    }

    private fun filterByChips(query : String){
        tempList.clear()
        adapterProduct.removeItems()
        val searchText = query!!.lowercase()
        if(searchText.isNotEmpty()){
            listProduct.forEach{
                if(it.faceshape?.lowercase()?.contains(searchText)  == true){
                    tempList.add(it)
                }
            }
            adapterProduct.addItems(tempList)
        }else{
            tempList.clear()
            adapterProduct.removeItems()
            tempList.addAll(listProduct)
            adapterProduct.addItems(tempList)
        }
    }
}