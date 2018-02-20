package com.atrio.smartdeal

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.location.*
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.atrio.smartdeal.Alert.GPSAlert
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.FusedLocationProviderApi
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.places.Place
import com.google.android.gms.location.places.Places
import com.google.android.gms.location.places.ui.PlacePicker
import com.google.android.gms.tasks.OnSuccessListener
import kotlinx.android.synthetic.main.activity_location.*
import java.util.*

class LocationActivity : AppCompatActivity(), GoogleApiClient.OnConnectionFailedListener, GoogleApiClient.ConnectionCallbacks, com.google.android.gms.location.LocationListener {

    lateinit var googleApiclient: GoogleApiClient
    private val PLACE_PICKER_REQUEST = 2

    private var mLocationManager: LocationManager? = null
    lateinit var mLocation: Location
    private var mLocationRequest: LocationRequest? = null
    private val listener: com.google.android.gms.location.LocationListener? = null
    private val UPDATE_INTERVAL = (2 * 1000).toLong()  /* 10 secs */
    private val FASTEST_INTERVAL: Long = 2000 /* 2 sec */
    lateinit var locationManager: LocationManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_location)

        googleApiclient = GoogleApiClient.Builder(this)
                .addOnConnectionFailedListener(this)
                .addConnectionCallbacks(this)
                .addApi(LocationServices.API).addApi(Places.GEO_DATA_API).addApi(Places.PLACE_DETECTION_API)
                .build()

        mLocationManager = this.getSystemService(Context.LOCATION_SERVICE) as LocationManager

        checkGPS()

        tv_getloc.setOnClickListener(View.OnClickListener {
            getLocation()
        })
        et_location.setOnClickListener(View.OnClickListener {
            getLocation()
        })
        btn_locnext.setOnClickListener(View.OnClickListener {
            intent = Intent(this, CategoryActivity::class.java)
            startActivity(intent)
        })

    }

    private fun checkGPS(): Boolean {
        if (!isLocationEnabled())
            GPSAlert(this).showAlert()
        return isLocationEnabled()
    }

    private fun isLocationEnabled(): Boolean {
        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
    }

    private fun getLocation() {
        if (GooglePlayServiceCheck().isGooglePlayInstalled(this@LocationActivity)) {
            val builder: PlacePicker.IntentBuilder = PlacePicker.IntentBuilder()
            startActivityForResult(builder.build(this@LocationActivity), PLACE_PICKER_REQUEST)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {

            PLACE_PICKER_REQUEST -> {
                if (resultCode == Activity.RESULT_OK) {
                    val place: Place = PlacePicker.getPlace(this, data)
                    et_location.setText(place.address)
                }
            }
            else -> {
                Toast.makeText(this, "Unrecognized request code", Toast.LENGTH_SHORT).show()
            }
        }
    }

    @SuppressLint("MissingPermission")
    override fun onConnected(p0: Bundle?) {
        startLocationUpdates()
        val fusedLocationProviderClient:FusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        fusedLocationProviderClient.getLastLocation()
                .addOnSuccessListener(this, OnSuccessListener<Location> { location ->
                    if (location != null) {
                        mLocation = location
                        getAddress(mLocation.latitude, mLocation.longitude)
                    }
                })
    }

    private fun getAddress(latitude: Double, longitude: Double) {

        var geocoder = Geocoder(this, Locale.getDefault())

        var addresses:List<Address> = geocoder.getFromLocation(latitude, longitude, 1)
        Log.i("Address34: ",""+addresses)
        var loc1 = addresses.get(0).getAddressLine(0)

        et_location.setText("$loc1")
    }

    @SuppressLint("MissingPermission")
    private fun startLocationUpdates() {
        mLocationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(UPDATE_INTERVAL)
                .setFastestInterval(FASTEST_INTERVAL)
        // Request location updates
        LocationServices.FusedLocationApi.requestLocationUpdates(googleApiclient, mLocationRequest, this)
    }

    override fun onConnectionSuspended(p0: Int) {
        googleApiclient.connect();
    }

    override fun onConnectionFailed(p0: ConnectionResult) {
        Toast.makeText(this, "Network Problem", Toast.LENGTH_SHORT).show()
    }

    override fun onLocationChanged(location: Location) {
//        var msg = "Updated Location: Latitude " + location.longitude.toString() + location.longitude
//        getAddress(location.latitude, location.longitude)
//        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }


    override fun onStart() {
        super.onStart()
        googleApiclient.connect()
    }

    override fun onStop() {
        super.onStop()
        googleApiclient.disconnect()
    }

}
