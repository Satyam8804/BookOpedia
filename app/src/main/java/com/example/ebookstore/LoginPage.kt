package com.example.ebookstore

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.example.ebookstore.MainActivity
import com.example.ebookstore.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class LoginPage : AppCompatActivity() {

    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var password: EditText
    private lateinit var register:TextView
    private lateinit var loginButton: Button
    private lateinit var email:EditText
    private lateinit var sharedPreferences: SharedPreferences

    @SuppressLint("NewApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_login_page)

        window?.statusBarColor = ContextCompat.getColor(this, R.color.status)


       email = findViewById(R.id.emailId)
        password = findViewById(R.id.password)

        loginButton = findViewById(R.id.loginButton)
        register = findViewById(R.id.signupText)
        firebaseAuth = FirebaseAuth.getInstance()

        loginButton.setOnClickListener {
            val UserEmail= email.text.toString()
            val pass = password.text.toString()


            if (UserEmail.isNotEmpty() && pass.isNotEmpty() ) {
                firebaseAuth.signInWithEmailAndPassword(UserEmail, pass).addOnCompleteListener {
                    if (it.isSuccessful) {
                        val i: Intent = Intent(applicationContext, MainActivity::class.java)
                        startActivity(i);
                    } else {
                        Toast.makeText(this, it.exception.toString(), Toast.LENGTH_SHORT).show()
                    }
                }
            }

            val editor = sharedPreferences.edit()
            editor.putString("username", UserEmail)
            editor.putString("password", pass)
            editor.apply()
        }
        register.setOnClickListener {
            val intent: Intent = Intent(this, register_page::class.java)
            startActivity(intent)
        }
        sharedPreferences = getSharedPreferences("userData", Context.MODE_PRIVATE)
        email.setText(sharedPreferences.getString("username", ""))
        password.setText(sharedPreferences.getString("password", ""))
    }
    override fun onBackPressed() {
        finishAffinity()
    }
}