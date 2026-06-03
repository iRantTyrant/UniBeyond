package com.example.unibeyond.presentation.components.event_components

import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import org.maplibre.compose.camera.CameraPosition
import org.maplibre.compose.camera.rememberCameraState
import org.maplibre.compose.location.LocationPuck
import org.maplibre.compose.location.LocationTrackingEffect
import org.maplibre.compose.location.mostAccurateBearing
import org.maplibre.compose.location.rememberDefaultLocationProvider
import org.maplibre.compose.location.rememberDefaultOrientationProvider
import org.maplibre.compose.location.rememberUserLocationState
import org.maplibre.compose.map.MapOptions
import org.maplibre.compose.map.MaplibreMap
import org.maplibre.compose.map.OrnamentOptions
import org.maplibre.compose.style.BaseStyle
import org.maplibre.spatialk.geojson.Position

@Composable
fun MyMap() {

    val context = LocalContext.current

    // Launcher ΜΟΝΟ για να ζητήσουμε την άδεια αν δεν υπάρχει
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { _ -> }

    // Μόλις ανοίξει ο χάρτης, αν δεν υπάρχει άδεια, την ζητάμε
    LaunchedEffect(Unit) {
        val hasFine = ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
        val hasCoarse = ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED

        if (!hasFine && !hasCoarse) {
            permissionLauncher.launch(
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
            )
        }
    }

    val cameraState = rememberCameraState()

    // 🌟 1. Ορίζουμε την κάμερα (Default στην Αθήνα, αν δεν έχει πιάσει ακόμα GPS)

    // 🌟 2. Οι έτοιμοι Providers του MapLibre
    val locationProvider = rememberDefaultLocationProvider()
    val orientationProvider = rememberDefaultOrientationProvider()
    val locationState = rememberUserLocationState(locationProvider, orientationProvider)

    MaplibreMap(
        cameraState = cameraState, // Σωστή σύνδεση
        baseStyle = BaseStyle.Uri("https://tiles.openfreemap.org/styles/liberty"),
        options = MapOptions(
            ornamentOptions = OrnamentOptions(
                padding = PaddingValues(0.dp),
                isLogoEnabled = true,
                logoAlignment = Alignment.TopStart,
                isAttributionEnabled = true,
                isCompassEnabled = false,
                isScaleBarEnabled = true,
                scaleBarAlignment = Alignment.BottomStart
            )
        ),
    ) {
        // 🌟 3. Εμφάνιση της Μπλε Κουκκίδας του χρήστη
        LocationPuck(
            idPrefix = "user",
            location = locationState.location,
            bearing = locationState.mostAccurateBearing(),
            cameraState = cameraState, // 🌟 ΔΙΟΡΘΩΘΗΚΕ: από cameraState σε camera
        )

        // 🌟 4. Αυτόματο tracking (Ακολουθεί τον χρήστη live όταν κινείται!)
        LocationTrackingEffect(locationState = locationState) {
            val position = currentLocation.location?.position?.value
            if (position != null) {
                cameraState.animateTo(CameraPosition(target = position, zoom = 15.0)) // 🌟 ΔΙΟΡΘΩΘΗΚΕ: camera αντί cameraState
            }
        }
    }
}