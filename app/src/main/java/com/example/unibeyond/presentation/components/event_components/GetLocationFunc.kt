package com.example.unibeyond.presentation.components.event_components

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle

@SuppressLint("MissingPermission")
fun getUserCoordinates(context: Context, onResult: (lat: Double, lng: Double) -> Unit) {
    val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager

    // Ζητάμε κατά προτίμηση το GPS για μέγιστη ακρίβεια
    val provider = if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
        LocationManager.GPS_PROVIDER
    } else if (locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
        LocationManager.NETWORK_PROVIDER
    } else {
        null
    }

    if (provider == null) {
        onResult(37.9838, 23.7275) // Default Αθήνα αν όλα είναι κλειστά
        return
    }

    // 🌟 ΑΦΗΝΟΥΜΕ ΤΟ GPS ΝΑ ΑΚΟΥΕΙ ΣΥΝΕΧΕΙΑ
    locationManager.requestLocationUpdates(
        provider,
        1000L, // Ενημέρωση κάθε 1 δευτερόλεπτο
        1f,    // ή κάθε 1 μέτρο μετακίνησης
        object : LocationListener {
            override fun onLocationChanged(location: Location) {
                // Κάθε φορά που το κινητό βελτιώνει την ακρίβειά του ή μετακινείσαι,
                // στέλνει τις νέες συντεταγμένες (π.χ. από Αθήνα -> Πάτρα)
                onResult(location.latitude, location.longitude)

                // 🔴 Σημείωση: Αφαιρέσαμε το removeUpdates(this) για να μην κλείνει αμέσως!
            }
            @Deprecated("Deprecated in Java")
            override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {}
            override fun onProviderEnabled(provider: String) {}
            override fun onProviderDisabled(provider: String) {}
        }
    )
}