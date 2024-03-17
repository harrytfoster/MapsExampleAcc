package com.example.mapsexampleacc

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import androidx.compose.material3.Text
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Configuration.getInstance().load(this, getPreferences(MODE_PRIVATE)) //Need this for load stuff
        setContent {
            NewMap()
        }
    }
}

@Composable
fun MapContent() { // View Map
    val context =
        LocalContext.current
    AndroidView(
        modifier = Modifier.fillMaxSize(),
        factory = {
                context ->
            MapView(context).apply()
            {controller.setCenter(GeoPoint(50.9, -1.4)) // Set geopoints
                controller.setZoom(14.0) // Set zoom
                setTileSource(TileSourceFactory.MAPNIK)
                isClickable =true } // Makes map clickable
        }

    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewMap(){
    var latitude by remember { mutableStateOf("") }
    var longitude by remember { mutableStateOf("") }
    var SearchLat by remember { mutableStateOf(0.0) }
    var SearchLon by remember { mutableStateOf(0.0) }
    BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
        AndroidView(modifier = Modifier.height(this.maxHeight-115.dp),factory = {
                context ->
            MapView(context).apply()
            {controller.setCenter(GeoPoint(SearchLat, SearchLon))
                controller.setZoom(14.0)
                setTileSource(TileSourceFactory.MAPNIK)
                isClickable =true
            }})
        TextField(value = latitude, onValueChange = {latitude=it}, modifier = Modifier
            .align(Alignment.BottomStart)
            .padding(16.dp))
        TextField(value = longitude, onValueChange = {longitude=it}, modifier = Modifier
            .align(Alignment.BottomCenter)
            .padding(16.dp))
        Button(onClick = { SearchLat = latitude.toDouble(); SearchLon = longitude.toDouble()}, modifier = Modifier
            .align(Alignment.BottomEnd)
            .padding(16.dp)) {("Go!")}
    }
}