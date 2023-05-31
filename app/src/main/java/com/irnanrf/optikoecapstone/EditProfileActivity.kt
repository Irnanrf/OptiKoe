package com.irnanrf.optikoecapstone

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.irnanrf.optikoecapstone.data.model.User
import com.irnanrf.optikoecapstone.databinding.ActivityEditProfileBinding
import koleton.api.hideSkeleton
import koleton.api.loadSkeleton
import java.util.*

class EditProfileActivity : AppCompatActivity() {

    private lateinit var binding : ActivityEditProfileBinding

    // Firebase Auth
    var firebaseAuth = FirebaseAuth.getInstance()
    // Realtime Database
    var database = FirebaseDatabase.getInstance()
    var mDatabase = database.getReference("users")
    // Firebase Storage
    var storage = FirebaseStorage.getInstance()

    var userImg = ""
    private lateinit var selectedImg : Uri

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        //load skeleton
        binding.linProfileImage.loadSkeleton()
        binding.lineInfoProfile.loadSkeleton()
        binding.lineInfoSave.loadSkeleton()


        //set editable
        binding.txtEmail.isEnabled = false;
        showProgress()
        setupAction()
        loadData()

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (data!=null){
            if(data.data != null){
                selectedImg = data.data!!
                binding.imgProfile.setImageURI(selectedImg)
            }
        }
    }

    private fun setupAction() {
        binding.btnSave.setOnClickListener {
            showProgress()
            if(binding.txtName.text != null && binding.txtEmail.text != null && binding.txtPhone.text != null){
                if(this::selectedImg.isInitialized){
                    uploadImage()
                } else{
                    uploadInfo(userImg)
                }

            }
        }

        binding.txtEditProfilePicture.setOnClickListener {
            val intent = Intent()
            intent.action = Intent.ACTION_GET_CONTENT
            intent.type = "image/*"
            startActivityForResult(intent, 1)
        }
    }

    private fun uploadImage(){
        showProgress()
        val reference = storage.reference.child("userprofile").child(Date().time.toString())

            reference.putFile(selectedImg).addOnCompleteListener{
                if(it.isSuccessful){
                    reference.downloadUrl.addOnSuccessListener { task ->
                        uploadInfo(task.toString())
                    }
                }

            }


    }

    private fun uploadInfo(imgUrl : String){
        showProgress()
        val userId = firebaseAuth.currentUser?.uid.toString()
        val userName = binding.txtName.text.toString()
        val userPhone = binding.txtPhone.text.toString()
        val userAddress = binding.txtAddress.text.toString()
        val dataUser = User(userName,userPhone,userAddress, imgUrl)

        mDatabase.child(userId).setValue(dataUser).addOnCompleteListener {
            Toast.makeText(this,"Data Updated", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, ProfileActivity::class.java))
            finish()
            binding.progressEditProfile.visibility = View.INVISIBLE
        }

    }

    private fun loadData(){
        binding.progressEditProfile.visibility = View.VISIBLE
        val userId = firebaseAuth.currentUser?.uid
        if (userId != null) {
            mDatabase.child(userId).get().addOnSuccessListener {
                Log.i("firebase", "Got value ${it.value}")
                binding.txtName.setText(it.child("name").value.toString())
                binding.txtPhone.setText(it.child("phone").value.toString())
                binding.txtAddress.setText(it.child("address").value.toString())
                binding.txtEmail.setText(firebaseAuth.currentUser?.email)
                Glide.with(this)
                    .load(it.child("imgpath").value.toString())
                    .into(binding.imgProfile)

                // Get image uri
                userImg = it.child("imgpath").value.toString()

                //hide skeleton
                binding.linProfileImage.hideSkeleton()
                binding.lineInfoProfile.hideSkeleton()
                binding.lineInfoSave.hideSkeleton()

                binding.progressEditProfile.visibility = View.INVISIBLE
            }.addOnFailureListener{
                Log.e("firebase", "Error getting data", it)
                binding.progressEditProfile.visibility = View.INVISIBLE
            }
        }
    }

    private fun showProgress(){
        if(binding.progressEditProfile.isVisible){
            binding.progressEditProfile.visibility = View.INVISIBLE
        } else{
            binding.progressEditProfile.visibility = View.VISIBLE
        }
    }


}