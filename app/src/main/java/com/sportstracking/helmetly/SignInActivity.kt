package com.sportstracking.helmetly

import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.sportstracking.helmetly.ads.Interstitial
import com.sportstracking.helmetly.data.TeamArray.Team
import com.sportstracking.helmetly.databinding.ActivitySignInBinding
import com.sportstracking.helmetly.ui.selection.FavoriteSelectionActivity
import com.sportstracking.helmetly.utility.SharedPrefHelper
import com.sportstracking.helmetly.utility.TinyDB
import com.sportstracking.helmetly.utility.Utility


class SignInActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignInBinding
    private lateinit var mGoogleSignInClient: GoogleSignInClient
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth


        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)

        binding.signInButton.setOnClickListener {
            signIn()
        }

        if (intent.extras?.getBoolean(SIGN_OUT, false) == true) {
            signOut()
        }
    }

    private fun setImage() {

        val screenWidth = Integer.parseInt(Utility().getScreenSize(this)[0])
        val screenHeight = Integer.parseInt(Utility().getScreenSize(this)[1])

        Glide.with(this)
            .asDrawable()
            .load("${API_URL}${screenWidth}x${screenHeight}?motorsport&${Math.random()}")
            .into(object : CustomTarget<Drawable>() {
                override fun onResourceReady(
                    resource: Drawable,
                    transition: Transition<in Drawable>?
                ) {
                    binding.root.background = resource
                }

                override fun onLoadCleared(placeholder: Drawable?) {}
            })
    }

    override fun onResume() {
        super.onResume()
        setImage()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Log.d(TAG, requestCode.toString())
        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)!!
                Log.d(TAG, "firebaseAuthWithGoogle:" + account.id)
                firebaseAuthWithGoogle(account.idToken!!)
            } catch (e: ApiException) {
                // Google Sign In failed, update UI accordingly
                Log.d(TAG, "Google sign in failed", e)
                updateUI(null)
            }
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithCredential:success")
                    val user = auth.currentUser
                    updateUI(user)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.d(TAG, "signInWithCredential:failure", task.exception)
                    updateUI(null)
                }
            }
    }


    private fun signIn() {
        val signInIntent = mGoogleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    private fun signOut() {

        auth.signOut()

        mGoogleSignInClient.signOut().addOnCompleteListener(this) {
        }
    }

    override fun onStart() {
        super.onStart()

        // Check for existing Google Sign In account, if the user is already signed in
        val currentUser = auth.currentUser
        updateUI(currentUser)
    }

    private fun updateUI(user: FirebaseUser?) {
        if (user != null) {
            val sharedPrefHelper = SharedPrefHelper(this)
            SharedPrefHelper.UID = user.uid

            //User Info
            sharedPrefHelper.insertString(getString(R.string.sign_in_email), user.email.toString())
            sharedPrefHelper.insertString(
                getString(R.string.sign_in_name),
                user.displayName.toString()
            )
            sharedPrefHelper.insertString(
                getString(R.string.sign_in_avatar),
                user.photoUrl.toString()
            )
            val favTeams =
                TinyDB(this).getListObject("${SharedPrefHelper.UID}_FAV_TEAMS", Team::class.java)

            if (favTeams.isEmpty()) {
                startActivity(Intent(this, FavoriteSelectionActivity::class.java))
            } else {
                startActivity(Intent(this, HomeActivity::class.java))
            }
            finish()
        } else {
            Interstitial(this)
        }
    }


    companion object {
        private const val TAG = "SignIn"
        private const val RC_SIGN_IN = 9001
        private const val API_URL = "https://source.unsplash.com/"
        const val SIGN_OUT = "SignOut"
    }
}