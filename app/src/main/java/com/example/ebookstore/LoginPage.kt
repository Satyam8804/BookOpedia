package com.example.ebookstore

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.ebookstore.MainActivity
import com.example.ebookstore.R
import com.google.firebase.auth.FirebaseAuth

class LoginPage : AppCompatActivity() {

    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var username: EditText
    private lateinit var password: EditText
    private lateinit var register:TextView
    private lateinit var loginButton: Button
    private lateinit var email:EditText
    private lateinit var sharedPreferences: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_login_page)
       email = findViewById(R.id.emailId)
        password = findViewById(R.id.password)

        loginButton = findViewById(R.id.loginButton)
        register = findViewById(R.id.signupText)
        firebaseAuth = FirebaseAuth.getInstance()

        loginButton.setOnClickListener {
            val email= username.text.toString()
            val pass = password.text.toString()
//            val regno = regNo.text.toString()
            if (email.isNotEmpty() && pass.isNotEmpty() ) {
                firebaseAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener {
                    if (it.isSuccessful) {
                        val i: Intent = Intent(applicationContext, MainActivity::class.java)
                        startActivity(i);
                    } else {
                        Toast.makeText(this, it.exception.toString(), Toast.LENGTH_SHORT).show()
                    }
                }
            }
            val editor = sharedPreferences.edit()
            editor.putString("username", email)
            editor.putString("password", pass)
            editor.apply()
        }
        register.setOnClickListener {
            val intent: Intent = Intent(this, register_page::class.java)
            startActivity(intent)
        }
        sharedPreferences = getSharedPreferences("userData", Context.MODE_PRIVATE)
        username.setText(sharedPreferences.getString("username", ""))
        password.setText(sharedPreferences.getString("password", ""))
    }
    override fun onBackPressed() {
        finishAffinity()
    }
}