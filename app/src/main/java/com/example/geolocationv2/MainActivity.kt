package com.example.geolocationv2

import android.Manifest
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
//import android.os.Build
import android.os.Bundle
import android.text.Html
//import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.motion.widget.Debug.getLocation
import androidx.core.app.ActivityCompat
//import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import java.io.IOException
import java.util.*

class MainActivity : AppCompatActivity() {
    //val TAG = "MainActivityGPS"
    val MY_PERMISSIONS_REQUEST_LOCATION = 1
    lateinit var bt_location: Button
    lateinit var textView1:TextView
    lateinit var textView2:TextView
    lateinit var textView3:TextView
    lateinit var textView4:TextView
    lateinit var textView5:TextView

    private lateinit var fusedLocationClient: FusedLocationProviderClient
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bt_location = findViewById(R.id.bt_location)
        textView1 = findViewById(R.id.text_view1)
        textView2 = findViewById(R.id.text_view2)
        textView3 = findViewById(R.id.text_view3)
        textView4 = findViewById(R.id.text_view4)
        textView5 = findViewById(R.id.text_view5)


        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        bt_location.setOnClickListener {
            getLocation()
        }
        fun getLocation() {
            if (ActivityCompat.checkSelfPermission(
                        this,
                        Manifest.permission.ACCESS_FINE_LOCATION
                    ) != PackageManager.PERMISSION_GRANTED
                    &&
                    ActivityCompat.checkSelfPermission(
                        this,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    ActivityCompat.requestPermissions(
                        this,
                        arrayOf(
                            Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION
                        ),
                        44
                    )
                } else {
                    fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                        if (location != null) {
                            try {
                                val geocoder = Geocoder(this, Locale.getDefault())
                                val addresses: List<Address> = geocoder.getFromLocation(location.latitude, location.longitude, 1) as List<Address>

                                textView1.text = Html.fromHtml("<font color='#6200EE'><b>Latitude : </b><br></font>" + addresses[0].latitude
                                )
                                textView2.text = Html.fromHtml("<font color='#6200EE'><b>Longitude : </b><br></font>" + addresses[0].longitude
                                )
                                textView3.text = Html.fromHtml("<font color='#6200EE'><b>Nom de pays : </b><br></font>" + addresses[0].countryName
                                )
                                textView4.text = Html.fromHtml("<font color='#6200EE'><b>Localité : </b><br></font>" + addresses[0].locality
                                )
                                textView5.text = Html.fromHtml("<font color='#6200EE'><b>Adresse : </b><br></font>" + addresses[0].getAddressLine(0)
                                )
                            } catch (e: IOException) {
                                e.printStackTrace()
                            }
                        } else {
                            Toast.makeText(
                                applicationContext, "Aucune position enregistrée", Toast.LENGTH_SHORT).show()
                        }
                    }.addOnFailureListener { e -> Toast.makeText(
                            applicationContext, e.message, Toast.LENGTH_SHORT).show()
                    }
        }
            }
        }
    }
}