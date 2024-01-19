package com.mikolajnowacki.amprojectapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.mikolajnowacki.amprojectapp.databinding.ActivityLoginBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.lang.Exception

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    lateinit var auth: FirebaseAuth
    lateinit var db:FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()


        binding.buttonRegister.setOnClickListener {
            registerUser()
        }

        binding.buttonLogin.setOnClickListener {
            loginUser()
        }

    }

    private fun registerUser() {
        val email = binding.editTextRegisterEmail.text.toString()
        val password = binding.editTextRegisterPassword.text.toString()
        if (email.isNotEmpty() && password.isNotEmpty()) {
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    auth.createUserWithEmailAndPassword(email, password).await()
                    withContext(Dispatchers.Main) {
                        Toast.makeText(
                            this@LoginActivity,
                            "New user registered!",
                            Toast.LENGTH_SHORT
                        ).show()
                        addNewCollection(email)
                    }
                } catch (e: Exception) {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(this@LoginActivity, e.message, Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }

    private fun loginUser() {
        val email = binding.editTextLoginEmail.text.toString()
        val password = binding.editTextLoginPassword.text.toString()
        if (email.isNotEmpty() && password.isNotEmpty()) {
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    auth.signInWithEmailAndPassword(email, password).await()
                    withContext(Dispatchers.Main) {
                        goToMainActivity(email)
                    }
                } catch (e: Exception) {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(this@LoginActivity, e.message, Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }

    private fun addNewCollection(collectionName: String) {
        val newCollection = hashMapOf<String, Any>()
        try {
            db.collection(collectionName).document().set(newCollection)
        } catch (e: Exception) {
            Toast.makeText(this@LoginActivity, e.message, Toast.LENGTH_LONG).show()
        }
    }

    private fun signOut() {

    }


    private fun goToMainActivity(user: String) {
        val mainActivityIntent = Intent(this, MainActivity::class.java)
        mainActivityIntent.putExtra("userID", user)
        startActivity(mainActivityIntent)
    }
}