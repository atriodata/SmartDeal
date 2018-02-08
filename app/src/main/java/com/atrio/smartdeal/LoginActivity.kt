package com.atrio.smartdeal

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import com.hbb20.CountryCodePicker
import kotlinx.android.synthetic.main.activity_login.*
import android.support.v4.content.ContextCompat.startActivity
import android.content.Intent
import android.widget.Toast


class LoginActivity : AppCompatActivity(), CountryCodePicker.OnCountryChangeListener, View.OnClickListener {
    var isd_code: String?=null
    var phn_no: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        isd_code = sp_country.getSelectedCountryCodeWithPlus()
        Log.i("onVerify22", "" +isd_code)
        sp_country.setOnCountryChangeListener(this)
        btn_next.setOnClickListener(this)

    }
    override fun onClick(v: View?) {
        if (validatePhoneNumber()) {
            phn_no = isd_code + et_phone.getText().toString().trim()
            intent = Intent(this, Verify_Otp_Activity::class.java)
            intent.putExtra("phn_number", phn_no)
            Log.i("onCodeSent4:", "" +phn_no)
            startActivity(intent)
            finish()
        }
    }

    override fun onCountrySelected() {
        isd_code = sp_country.getSelectedCountryCodeWithPlus()
        Toast.makeText(this, "" + sp_country.getSelectedCountryCodeWithPlus(), Toast.LENGTH_SHORT).show();
    }

    private fun validatePhoneNumber(): Boolean {
        if (!TextUtils.isEmpty(et_phone.getText().toString())) {
            return true
        }
        et_phone.setError("Invalid phone number.")
        return false
    }
}
