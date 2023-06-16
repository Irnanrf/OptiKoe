package com.irnanrf.optikoecapstone.ar

import android.Manifest
import android.os.Bundle
import android.view.View
import com.google.ar.core.Config
import com.google.ar.core.Session
import com.google.ar.sceneform.ux.ArFragment
import java.util.*


 class FaceFragment : ArFragment() {
    override fun getSessionConfiguration(session: Session?): Config {
        val config = Config(session)
        config.augmentedFaceMode = Config.AugmentedFaceMode.MESH3D
        return config
    }

    override fun getSessionFeatures(): MutableSet<Session.Feature> {
        return EnumSet.of(Session.Feature.FRONT_CAMERA)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        planeDiscoveryController.hide()
        planeDiscoveryController.setInstructionView(null)
    }
     override fun getAdditionalPermissions(): Array<String> {
         val additionalPermissions = super.getAdditionalPermissions()
         val permissionsLength = additionalPermissions.size
         val permissions = Array(permissionsLength + 1) { Manifest.permission.WRITE_EXTERNAL_STORAGE }
         if(permissionsLength > 0) {
             System.arraycopy(additionalPermissions, 0, permissions, 1, permissionsLength)
         }
         return permissions
     }
}