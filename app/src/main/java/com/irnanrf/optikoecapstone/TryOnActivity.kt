package com.irnanrf.optikoecapstone

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.google.ar.core.AugmentedFace
import com.google.ar.core.TrackingState
import com.google.ar.sceneform.ArSceneView
import com.google.ar.sceneform.Scene
import com.google.ar.sceneform.rendering.ModelRenderable
import com.google.ar.sceneform.rendering.Renderable
import com.google.ar.sceneform.rendering.Texture
import com.google.ar.sceneform.ux.ArFragment
import com.google.ar.sceneform.ux.AugmentedFaceNode
import com.irnanrf.optikoecapstone.databinding.ActivityTryOnBinding

class TryOnActivity : AppCompatActivity() {

    private lateinit var binding : ActivityTryOnBinding

    private lateinit var scene: Scene
    private lateinit var sceneView: ArSceneView
    lateinit var arFragment: ArFragment
    val modelArray : ArrayList<ModelRenderable> = arrayListOf()
    var faceNodeMap = HashMap<AugmentedFace, AugmentedFaceNode>()
    private var regionsRenderable: ModelRenderable? = null
    private  var directory = R.raw.yellow_sunglasses
    private  var filter : Boolean  = false
    private  var filterTexture: Texture? =null

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTryOnBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setTitle("Visual Try On")

        ModelRenderable.builder()
            .setSource(this, directory)
            .build()
            .thenAccept { modelRenderable ->
                modelArray.add(modelRenderable)
                regionsRenderable = modelRenderable
                modelRenderable.isShadowCaster = false
                modelRenderable.isShadowReceiver = false
            }

        arFragment = supportFragmentManager.findFragmentById(R.id.fragment) as ArFragment

        binding.btnBack.setOnClickListener{
            onBackPressed()
        }

        setupScene()
        addOnUpdateListener()
    }

    private fun addOnUpdateListener() {
        arFragment.arSceneView.scene.addOnUpdateListener {
            if(regionsRenderable != null ) {
                addTrackedFaces()
                removeUntrackedFaces()
            }
        }
    }

    private fun setupScene() {
        sceneView = arFragment.arSceneView
        sceneView.cameraStreamRenderPriority = Renderable.RENDER_PRIORITY_FIRST
        scene = sceneView.scene

    }

    private fun addTrackedFaces() {
        val session = arFragment.arSceneView.session ?: return
        session.getAllTrackables(AugmentedFace::class.java).let {
            for (face in it) {
                Log.e("face  found", "found face")
                if (!faceNodeMap.containsKey(face)) {
                    val faceNode = AugmentedFaceNode(face)
                    faceNode.setParent(scene)
                    faceNode.faceRegionsRenderable = regionsRenderable
                    faceNodeMap[face] = faceNode
                    faceNodeMap.getValue(face).faceMeshTexture = null

                }else if(filter){
                    faceNodeMap.getValue(face).faceMeshTexture = filterTexture
                    faceNodeMap.getValue(face).faceRegionsRenderable = null
                } else{
                    faceNodeMap.getValue(face).faceRegionsRenderable = regionsRenderable
                    faceNodeMap.getValue(face).faceMeshTexture = null

                }

            }
        }
    }
    private fun removeUntrackedFaces() {
        val entries = faceNodeMap.entries
        for(entry in entries) {
            val face = entry.key
            if(face.trackingState == TrackingState.STOPPED) {
                val faceNode = entry.value
                faceNode.setParent(null)
                entries.remove(entry)
            }
        }
    }
}