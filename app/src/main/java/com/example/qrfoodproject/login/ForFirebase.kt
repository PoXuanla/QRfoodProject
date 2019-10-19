package com.example.qrfoodproject.login


import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider


class ForFirebase : AppCompatActivity(){

    private val Request_code = 7
    private lateinit var mGoogleSignInClient : GoogleSignInClient
    private lateinit var mAuth: FirebaseAuth

    private var token = "586856969924-dtmiauot0h0g7gmdbc4vt3lsabjuno3u.apps.googleusercontent.com"

    //val catchAct = MainActivity::class.java

    private fun call(mainActivity: MainActivity) {

        mAuth = FirebaseAuth.getInstance()

        val googleSignIn = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(token)
                .requestEmail().build()

        mGoogleSignInClient = GoogleSignIn.getClient(mainActivity, googleSignIn)


    }

    fun signIn(mainActivity: MainActivity) {

        call(mainActivity)
        //problem?

        val sign = mGoogleSignInClient.signInIntent
        //problem?
        mainActivity.startActivityForResult(sign, Request_code)

    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == Request_code){
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)

            try{
                //Google Signed In successfully, getting auth with firebase
                val account = task.getResult(ApiException::class.java)
                fireBaseAuthOfGoogle(account!!)
                //!! means non-null, use it because if not it will be expected as "GoogleSignInAccount?"
                //not sure if it was right, neither
            }catch (e : ApiException){  Log.e("error", "failed at task.getResult statement")}

        }
    }

    public override fun onStart() {
        //check if user signed in
        super.onStart()
        val currentUser = mAuth.currentUser
        updateUI(currentUser)
    }

    private fun updateUI(currentUser: FirebaseUser?) {
        if (currentUser != null)
            Toast.makeText(this, "Welcome back, ${currentUser.displayName}",Toast.LENGTH_LONG).show()
    }

    private fun fireBaseAuthOfGoogle(account: GoogleSignInAccount) {

        Log.d("AuthOfGoogle","Login via Firebase of Google in auth: " + account.id!!)

        var pass =  GoogleAuthProvider.getCredential(account.idToken, null)
        mAuth.signInWithCredential(pass)
                .addOnCompleteListener(this){
                    task -> if (task.isSuccessful){
                        //Sign in success then
                        Log.d("Login statement", "Success!!")
                        val user = mAuth.currentUser
                        updateUI(user)
                    }
                    else{
                        //Sign in failure then
                        Log.d("Login statement", "Failed...", task.exception)
                        Toast.makeText(this, "Couldn't get user's auth from firebase exists data", Toast.LENGTH_LONG).show()
                        updateUI(null)
                    }
                }
    }

}