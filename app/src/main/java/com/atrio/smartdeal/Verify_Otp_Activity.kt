package com.atrio.smartdeal

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import kotlinx.android.synthetic.main.activity_verify__otp_.*
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.*
import java.util.concurrent.TimeUnit
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.FirebaseException
import com.google.firebase.auth.PhoneAuthCredential






class Verify_Otp_Activity : AppCompatActivity() {
    var User: FirebaseUser?=null
    lateinit var mAuth: FirebaseAuth
    lateinit var mCallbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks
    private var mVerificationId: String? = null
    private var otp: String? = null
    var phn_no: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_verify__otp_)
        phn_no = intent.getStringExtra("phn_number")
        Log.i("onVerify", "" + this.phn_no)

        mAuth = FirebaseAuth.getInstance()
        if (User!=null){
            User = mAuth.currentUser!!
        }

        mCallbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                signInWithPhoneAuthCredential(credential)
            }

            override fun onVerificationFailed(e: FirebaseException) {
                if (e is FirebaseAuthInvalidCredentialsException) {
                    Log.d("Invalid request", "Invalid request")
                } else if (e is FirebaseTooManyRequestsException) {
                    Toast.makeText(this@Verify_Otp_Activity, "sms_qutoa_exceeded_alert", Toast.LENGTH_SHORT).show();
                }
            }

            override fun onCodeSent(verificationId: String,
                                    token: PhoneAuthProvider.ForceResendingToken?) {

                mVerificationId = verificationId
                Toast.makeText(this@Verify_Otp_Activity, "otp_send_message", Toast.LENGTH_SHORT).show()
            }

           override fun onCodeAutoRetrievalTimeOut(s: String) {
                super.onCodeAutoRetrievalTimeOut(s)
                otp = et_otp.getText().toString().trim()
                if (TextUtils.isEmpty(otp)) {
                    Toast.makeText(this@Verify_Otp_Activity, "otp_blank_alert", Toast.LENGTH_SHORT).show()
                    return
                }
                signInWithPhoneAuthCredential(PhoneAuthProvider.getCredential(s,otp!!))
            }
        }

        btn_verify.setOnClickListener(View.OnClickListener {
            if (TextUtils.isEmpty(et_otp.getText().toString().trim())) {
                Toast.makeText(this, "otp_blank_alert", Toast.LENGTH_SHORT).show()
            }
            otp = et_otp.getText().toString().trim()
            signInWithPhoneAuthCredential(PhoneAuthProvider.getCredential(mVerificationId!!,otp!!))
        })
        btn_resend.setOnClickListener(View.OnClickListener {
            fireBasePhLogin(phn_no!!)
        })
    }

    override fun onStart() {
        super.onStart()
        fireBasePhLogin(phn_no!!)
    }

    private fun fireBasePhLogin(phoneNumber: String) {
        Log.i("onCodeSent422:", "" + phoneNumber)
        PhoneAuthProvider.getInstance().verifyPhoneNumber(phoneNumber, 60, TimeUnit.SECONDS, this , mCallbacks)
    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        this.mAuth.signInWithCredential(credential).addOnCompleteListener(this, object : OnCompleteListener<AuthResult>{
            override fun onComplete(task: Task<AuthResult>) {
                if (task.isSuccessful()) {
                    User = (task.getResult() as AuthResult).user
                    Log.d("", "onComplete: " + User)
                    Toast.makeText(this@Verify_Otp_Activity, "Ph number verified", Toast.LENGTH_SHORT).show()
                    intent = Intent(this@Verify_Otp_Activity, MainActivity::class.java)
                    startActivity(intent)
                } else if (task.getException() is FirebaseAuthInvalidCredentialsException) {
                    Toast.makeText(this@Verify_Otp_Activity, "wrong_verification_code_alert", Toast.LENGTH_SHORT).show()
                }
            }

        })
    }
}
