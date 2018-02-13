package com.atrio.smartdeal

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.View
import android.widget.Toast
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.places.Place
import com.google.android.gms.location.places.Places
import com.google.android.gms.location.places.ui.PlacePicker
import kotlinx.android.synthetic.main.activity_location.*

class LocationActivity : AppCompatActivity(), GoogleApiClient.OnConnectionFailedListener {

    lateinit var googleApiclient: GoogleApiClient
    private val PLACE_PICKER_REQUEST = 2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_location)

        googleApiclient = GoogleApiClient.Builder(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API).addApi(Places.GEO_DATA_API).addApi(Places.PLACE_DETECTION_API)
                .build()

        et_location.setOnClickListener(View.OnClickListener {
            getLocation()
        })
       btn_locnext.setOnClickListener(View.OnClickListener {
           intent = Intent(this, MainActivity::class.java)
           startActivity(intent)
       })

    }
    private fun getLocation() {
        Log.i("intentclick", "getlocation")
        if (GooglePlayServiceCheck().isGooglePlayInstalled(this@LocationActivity)) {
            val builder: PlacePicker.IntentBuilder = PlacePicker.IntentBuilder()
            startActivityForResult(builder.build(this@LocationActivity), PLACE_PICKER_REQUEST)
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {

            PLACE_PICKER_REQUEST -> {
                if (resultCode == Activity.RESULT_OK) {
                    val place: Place = PlacePicker.getPlace(data, this)
                    et_location.setText(place.address)
                }
            }
            else -> {
                Toast.makeText(this, "Unrecognized request code", Toast.LENGTH_SHORT).show()
            }
        }
    }


    override fun onConnectionFailed(p0: ConnectionResult) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
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
