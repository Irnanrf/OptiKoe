package com.irnanrf.optikoecapstone

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.media.ExifInterface
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.lifecycle.ViewModelProvider
import com.irnanrf.optikoecapstone.data.api.ResponseState
import com.irnanrf.optikoecapstone.databinding.ActivityScanFaceBinding
import com.irnanrf.optikoecapstone.util.createCustomTempFile
import com.irnanrf.optikoecapstone.util.reduceFileImage
import com.irnanrf.optikoecapstone.util.uriToFile
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

class ScanFaceActivity : AppCompatActivity() {

    private lateinit var binding : ActivityScanFaceBinding
    private lateinit var scanFaceViewModel: ScanFaceViewModel
    private lateinit var currentPhotoPath: String
    private var getFile: File? = null

    companion object {
        private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
        private const val REQUEST_CODE_PERMISSIONS = 10
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (!allPermissionsGranted()) {
                Toast.makeText(
                    this,
                    "Didnt get permission",
                    Toast.LENGTH_SHORT
                ).show()
                finish()
            }
        }
    }

    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(baseContext, it) == PackageManager.PERMISSION_GRANTED
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityScanFaceBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setTitle("FaceShape Detector")

        binding.progressScanFace.visibility = View.INVISIBLE
        binding.btnBackToHome.visibility = View.INVISIBLE

        if (!allPermissionsGranted()) {
            ActivityCompat.requestPermissions(
                this,
                REQUIRED_PERMISSIONS,
                REQUEST_CODE_PERMISSIONS
            )
        }
        setupViewModel()
        setupAction()

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun setupAction(){
        binding.btnCamera.setOnClickListener { startTakePhoto() }
        binding.btnGallery.setOnClickListener { startGallery() }
        binding.btnUpload.setOnClickListener {
            binding.progressScanFace.visibility = View.VISIBLE
            uploadImage() }
        binding.btnBackToHome.setOnClickListener {
            val i = Intent()
            i.putExtra("ActivityResult", binding.txtOutput.text.toString())
            setResult(RESULT_OK, i)
            finish()
        }
    }

    private fun setupViewModel() {
        val factory: ViewModelFactory = ViewModelFactory.getInstance(this)
        scanFaceViewModel = ViewModelProvider(this, factory)[ScanFaceViewModel::class.java]
    }

    private fun uploadImage() {
        if (getFile != null) {
            val file = reduceFileImage(getFile as File)

            val requestImageFile = file.asRequestBody("image/jpeg".toMediaTypeOrNull())
            val imageMultipart: MultipartBody.Part = MultipartBody.Part.createFormData(
                "file",
                file.name,
                requestImageFile
            )
            scanFaceViewModel.uploadFaceScan(imageMultipart).observe(this@ScanFaceActivity){
                when(it){
                    is ResponseState.Success -> {
                        Toast.makeText(this@ScanFaceActivity, "Scan Success", Toast.LENGTH_SHORT).show()
                        binding.progressScanFace.visibility = View.INVISIBLE
                        binding.btnBackToHome.visibility = View.VISIBLE
                        binding.txtOutput.text = it.data.data.faceshape
                    }
                    is ResponseState.Loading -> Toast.makeText(this@ScanFaceActivity,"Loading..", Toast.LENGTH_SHORT).show()
                    is ResponseState.Error ->{
                        Toast.makeText(this@ScanFaceActivity, it.error, Toast.LENGTH_SHORT).show()
                        binding.txtOutput.text = it.error
                        binding.progressScanFace.visibility = View.INVISIBLE
                    }
                }
            }
        } else {
            Toast.makeText(this@ScanFaceActivity, "Upload Image First!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun startGallery() {
        val intent = Intent()
        intent.action = Intent.ACTION_GET_CONTENT
        intent.type = "image/*"
        val chooser = Intent.createChooser(intent, "Choose a Picture")
        launcherIntentGallery.launch(chooser)
    }

    private fun startTakePhoto() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        intent.resolveActivity(packageManager)

        createCustomTempFile(application).also {
            val photoURI: Uri = FileProvider.getUriForFile(
                this@ScanFaceActivity,
                "com.irnanrf.optikoecapstone",
                it
            )
            currentPhotoPath = it.absolutePath
            intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
            launcherIntentCamera.launch(intent)
        }
    }

    private val launcherIntentGallery = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val selectedImg: Uri = result.data?.data as Uri

            val myFile = uriToFile(selectedImg, this@ScanFaceActivity)

            getFile = myFile

            binding.imgSelected.setImageURI(selectedImg)
        }
    }

    private val launcherIntentCamera = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == RESULT_OK) {
            val myFile = File(currentPhotoPath)
            getFile = myFile

            val result = rotateBitmap(
                BitmapFactory.decodeFile(getFile?.path),
                currentPhotoPath
            )
            binding.imgSelected.setImageBitmap(result)
        }
    }

    fun rotateBitmap(bitmap: Bitmap, filePath: String): Bitmap {
        val exif = ExifInterface(filePath)
        val orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_UNDEFINED)
        val matrix = Matrix()
        when (orientation) {
            ExifInterface.ORIENTATION_ROTATE_90 -> matrix.postRotate(90f)
            ExifInterface.ORIENTATION_ROTATE_180 -> matrix.postRotate(180f)
            ExifInterface.ORIENTATION_ROTATE_270 -> matrix.postRotate(270f)
        }
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)
    }


}