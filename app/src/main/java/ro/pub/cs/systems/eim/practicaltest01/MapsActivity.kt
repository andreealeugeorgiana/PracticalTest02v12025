package ro.pub.cs.systems.eim.practicaltest01
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)

        // Inițializare fragment hartă
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Task 4b: Centrare pe Ghelmegioaia
        val ghelmegioaia = LatLng(44.63, 22.88)

        mMap.addMarker(MarkerOptions().position(ghelmegioaia).title("Marker in Ghelmegioaia"))

        // Mutăm camera cu un zoom decent (ex: 12)
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(ghelmegioaia, 12f))
    }
}