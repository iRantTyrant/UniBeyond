package com.example.unibeyond.presentation.components.event_components

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.example.unibeyond.R
import kotlinx.coroutines.launch
import org.maplibre.compose.camera.CameraPosition
import org.maplibre.compose.camera.rememberCameraState
import org.maplibre.compose.location.LocationPuck
import org.maplibre.compose.location.mostAccurateBearing
import org.maplibre.compose.location.rememberDefaultLocationProvider
import org.maplibre.compose.location.rememberDefaultOrientationProvider
import org.maplibre.compose.location.rememberUserLocationState
import org.maplibre.compose.map.MapOptions
import org.maplibre.compose.map.MaplibreMap
import org.maplibre.compose.map.OrnamentOptions
import org.maplibre.compose.material3.DisappearingCompassButton
import org.maplibre.compose.material3.DisappearingScaleBar
import org.maplibre.compose.material3.ExpandingAttributionButton
import org.maplibre.compose.material3.LocationPuckDefaults
import org.maplibre.compose.style.BaseStyle
import org.maplibre.compose.style.rememberStyleState
import org.maplibre.spatialk.geojson.Position
import kotlin.time.Duration.Companion.seconds

@SuppressLint("LocalContextGetResourceValueCall")
@Composable
fun UniBeyondMap() {
    val context = LocalContext.current

    //-Hold in a state if we have the permission-
    var hasLocationPermission by remember {
        mutableStateOf(
            ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED ||
                    ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
        )
    }
    //-Hold in a state if we have the permission-

    //--Launcher for getting permission--
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        hasLocationPermission = permissions[Manifest.permission.ACCESS_FINE_LOCATION] == true ||
                permissions[Manifest.permission.ACCESS_COARSE_LOCATION] == true
    }
    //--Launcher for getting permission--

    //-When the map opens launch a check-
    LaunchedEffect(Unit) {
        if (!hasLocationPermission) {
            permissionLauncher.launch(
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
            )
        }
    }
    //-When the map opens launch a check-

    //If we don't have permission we open the map without pinpointing the user , this will keep the app from having a crash due to permission
    if (!hasLocationPermission) {
        MaplibreMap(
            baseStyle = BaseStyle.Uri("https://tiles.openfreemap.org/styles/liberty")
        )
        return
    }

    //-Get Providers through the MapLibre defaults-
    val locationProvider = rememberDefaultLocationProvider()
    val orientationProvider = rememberDefaultOrientationProvider()
    val locationState = rememberUserLocationState(locationProvider, orientationProvider)
    //-Get Providers through the MapLibre defaults-

    //Set the camera state and it's default
    val cameraState = rememberCameraState(
        firstPosition = CameraPosition(target = Position(23.7275,37.9838)) // Default to Athens, Greece
    )

    val styleState = rememberStyleState()//Requirement for using Material 3 ornaments and icons

    var followUser by remember {mutableStateOf(true)} // The flag that signals the launched effect for following the user

    //A launched effect that follows the user. Enabled by default so the camera can pan to the user
    //NOTE : There is something similar in the MapLibre library called TrackingEffect but it didn't work for me
    LaunchedEffect(locationState.location,followUser) {
        if(followUser) {
            //This keeps the app from crashing until or when we have the location
            if (locationState.location?.position?.value != null) {
                cameraState.animateTo(
                    finalPosition = CameraPosition(
                        target = locationState.location?.position?.value!!,
                        zoom = 15.0,
                    ),
                    duration = 5.seconds
                )
            }
        }
    }
    


    //We use the snackbarHostState to remember the state of the snackbar that we use to show messages / feedback to the user
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    //--Launched Effect to inform user how to disable or enable the follow mode--
    LaunchedEffect(Unit) {
        snackbarHostState.showSnackbar(
            message = context.getString(R.string.follow_mode_explanation)
        )
    }
    //--Launched Effect to inform user how to disable or enable the follow mode--

    //-------------Box that holds the map it's contents and all composables we want to show on top of the map---------------
    Box(Modifier.fillMaxSize()) {//We use a box to stack elements on top of each other
        //-------The map and it's contents-----
        MaplibreMap(
            cameraState = cameraState,
            baseStyle = BaseStyle.Uri("https://tiles.openfreemap.org/styles/positron"),
            styleState = styleState,
            options = MapOptions(ornamentOptions = OrnamentOptions.OnlyLogo)//Disable all default map ornaments.

        ) {//In here we only add Composables from the MapLibre

            //The user on the map indicated by a puck
            LocationPuck(
                idPrefix = "user",
                location = locationState.location,
                bearing = locationState.mostAccurateBearing(),//Show bearing from both providers
                cameraState = cameraState,
                colors = LocationPuckDefaults.colors(),//Get material3 Colors
                //On click enable / disable followUser and show corresponding message
                onClick = {
                    followUser = !followUser // Changes the state from true to false and reverse

                    // Start a coroutine to show the snackbar
                    scope.launch {
                        // Cancel any previous messages
                        snackbarHostState.currentSnackbarData?.dismiss()

                        // Select new message
                        val message = if (followUser) {
                            context.getString(R.string.following_the_user)
                        } else {
                            context.getString(R.string.not_following_the_user)
                        }

                        // Send message to snackbar
                        snackbarHostState.showSnackbar(message = message)
                    }

                }

            )

        }
        //-------The map and it's contents-----

        //---Box to hold the material3 ornaments for the map----
        Box(modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)){
            //A scale bar that disappears when not used, fades in and out when the user interacts with the map
            DisappearingScaleBar(
                metersPerDp = cameraState.metersPerDpAtTarget,
                zoom = cameraState.position.zoom,//Use the camera position zoom
                modifier = Modifier.align(Alignment.TopStart)//Put the scale bar ornament in the top left
            )
            DisappearingCompassButton(cameraState, modifier = Modifier.align(Alignment.TopEnd))//A disappearing compass,same use as the scale bar above
            //The attributions button
            ExpandingAttributionButton(
                cameraState = cameraState,
                styleState = styleState,
                modifier = Modifier.align(Alignment.BottomEnd),//Bottom right placement
                contentAlignment = Alignment.BottomEnd
            )


        }
        //---Box to hold the material3 ornaments for the map----

        //Card that holds a lazy column to show the active events - a List of EventCardSmall
        Card(modifier= Modifier
            .padding(start=20.dp,end=20.dp,bottom = 60.dp)
            .align(Alignment.BottomCenter)
            .fillMaxWidth()){
            Text(text= stringResource(R.string.test_text))
        }



        //--The snackbar to show messages--
        SnackbarHost(hostState = snackbarHostState,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 48.dp)){snackbarData -> //Custom snackbar elements
            Snackbar(
                snackbarData = snackbarData,
                shape = RoundedCornerShape(16.dp),
                containerColor = MaterialTheme.colorScheme.surfaceContainer,
                contentColor = MaterialTheme.colorScheme.onSurface
            )
        }
        //--The snackbar to show messages--
    }
    //-------------Box that holds the map it's contents and all composables we want to show on top of the map---------------
}
