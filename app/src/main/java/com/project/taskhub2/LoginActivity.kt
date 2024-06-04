package com.project.taskhub2

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.Firebase
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.MainScope

class LoginActivity: AppCompatActivity() {
    val coroutineScope = MainScope()
    val context = this

    lateinit var auth: FirebaseAuth
    lateinit var launcher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        auth = Firebase.auth

        launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(it.data)
            try {
                val account = task.getResult(ApiException::class.java)
                if(account != null) {
                    firebaseAuthWithGoogle(account.idToken!!)
                }
            }
            catch (e: ApiException) {
                Log.i("AUTH ERR", e.toString())
            }
        }

        val signIn = findViewById<Button>(R.id.buttonLoginGoogle);

        signIn.setOnClickListener {
            signInWithGoogle()
        }

    }

    private fun checkAuthState(): Boolean {
        return auth.currentUser != null
    }

    private fun getClient(): GoogleSignInClient {
        val gso = GoogleSignInOptions
                .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build()

        return GoogleSignIn.getClient(this, gso)
    }

    private fun signInWithGoogle() {
        val signInClient = getClient()
        launcher.launch(signInClient.signInIntent)
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential).addOnCompleteListener {
            if(it.isSuccessful) {
                val firebaseUser = auth.currentUser

                FirebaseApp.initializeApp(this)
                val database = FirebaseDatabase.getInstance()
                val userRef = database.getReference("User")

                val nick = firebaseUser?.displayName
                val email = firebaseUser?.email

                val uid = firebaseUser?.uid

                userRef.orderByChild("id").equalTo(uid).addListenerForSingleValueEvent(object :
                    ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        if(!dataSnapshot.exists()) {
                            val user = User(uid, nick ?: "", email ?: "")
                            userRef.push().setValue(user)
                        }

                        startActivity(Intent(context, MainActivity::class.java))
                        Toast.makeText(context, "Добро пожаловать!", Toast.LENGTH_SHORT).show()
                        Log.i("Auth Google", "successful")
                    }

                    override fun onCancelled(databaseError: DatabaseError) {
                        Log.i("Auth Google", "failed")
                    }
                })

            } else {
                Log.i("Auth Google", "failed")
            }
        }
    }
}


