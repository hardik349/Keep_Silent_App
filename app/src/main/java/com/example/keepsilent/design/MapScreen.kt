package com.example.keepsilent.design

    import androidx.compose.foundation.layout.fillMaxSize
    import androidx.compose.foundation.layout.padding
    import androidx.compose.material.rememberScaffoldState
    import androidx.compose.material.Scaffold
    import androidx.compose.material.icons.Icons
    import androidx.lifecycle.viewmodel.compose.viewModel
    import androidx.compose.material.icons.filled.ToggleOff
    import androidx.compose.material.icons.filled.ToggleOn
    import androidx.compose.material3.FloatingActionButton
    import androidx.compose.material3.Icon
    import androidx.compose.runtime.Composable
    import androidx.compose.runtime.remember
    import androidx.compose.ui.Modifier
    import androidx.compose.ui.tooling.preview.Preview
    import androidx.hilt.navigation.compose.hiltViewModel
    import androidx.lifecycle.ViewModel
    import com.example.keepsilent.googleMaps.MapEvent
    import com.example.keepsilent.googleMaps.MapsViewModel
    import com.google.android.gms.maps.model.BitmapDescriptorFactory
    import com.google.android.gms.maps.model.LatLng
    import com.google.maps.android.compose.GoogleMap
    import com.google.maps.android.compose.MapUiSettings
    import com.google.maps.android.compose.Marker



@Composable
fun MapScreen(
    viewModel: MapsViewModel = hiltViewModel(),
){
    val uiSettings = remember {
        MapUiSettings(zoomControlsEnabled = false)
    }
    val scaffoldState = rememberScaffoldState()
    Scaffold(
        scaffoldState = scaffoldState,
        floatingActionButton = {
            FloatingActionButton(onClick = { viewModel.Event(MapEvent.ToggleFalloutMap) }) {
                Icon(
                    imageVector = if(viewModel.state.isFallOut){
                        Icons.Default.ToggleOff
                    }else{
                        Icons.Default.ToggleOn
                    },
                    contentDescription = "floating toggle button"
                )
            }
        }
    ) { innerPadding ->
        GoogleMap(
            properties = viewModel.state.properties,
            uiSettings = uiSettings,
            onMapLongClick = {
              viewModel.Event(MapEvent.OnMapLongClick(it))
            },
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ){
            viewModel.state.locations.forEach {spot ->
                Marker(
                    position = LatLng(spot.lat, spot.lng),
                    title = "Silent Mode Location (${spot.lat},${spot.lng})",
                    snippet = "Long click to delete",
                    onInfoWindowLongClick ={
                        viewModel.Event(MapEvent.OnInfoWindowLongClick(spot))
                    },
                    onClick = {
                        it.showInfoWindow()
                        true
                    },
                    icon = BitmapDescriptorFactory.defaultMarker(
                        BitmapDescriptorFactory.HUE_YELLOW
                    )
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MapScreenPreview(){
    MapScreen()
}