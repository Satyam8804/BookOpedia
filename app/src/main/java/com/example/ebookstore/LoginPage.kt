package com.example.ebookstore

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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

        supportActionBar?.setBackgroundDrawable(ColorDrawable(ContextCompat.getColor(this, R.color.purple_200)))
        window?.statusBarColor = ContextCompat.getColor(this, R.color.purple_200)


       email = findViewById(R.id.emailId)
        password = findViewById(R.id.password)

        loginButton = findViewById(R.id.loginButton)
        register = findViewById(R.id.signupText)
        firebaseAuth = FirebaseAuth.getInstance()

        loginButton.setOnClickListener {
            val email= email.text.toString()
            val pass = password.text.toString()
//            val regno = regNo.text.toString()
            if (email.isNotEmpty() && pass.isNotEmpty()) {
                firebaseAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        // Authentication successful
                        val user = firebaseAuth.currentUser
                        if (user != null) {
                            val userId = user.uid

                            // Assuming you have a database reference to fetch userName
                            val databaseReference = FirebaseDatabase.getInstance().reference
                            val usersReference = databaseReference.child("Users")
                            val userQuery = usersReference.child(userId)

                            userQuery.addListenerForSingleValueEvent(object : ValueEventListener {
                                override fun onDataChange(dataSnapshot: DataSnapshot) {
                                    if (dataSnapshot.exists()) {
                                        val userName = dataSnapshot.child("name").getValue(String::class.java)

                                        if (userName != null) {
                                            val intent = Intent(applicationContext, MainActivity::class.java)
                                            intent.putExtra("userName", userName)
                                            startActivity(intent)
                                        } else {
                                            Toast.makeText(this@LoginPage, "Failed to fetch userName", Toast.LENGTH_SHORT).show()
                                        }
                                    } else {
                                        Toast.makeText(this@LoginPage, "User data not found", Toast.LENGTH_SHORT).show()
                                    }
                                }

                                override fun onCancelled(databaseError: DatabaseError) {
                                    Toast.makeText(this@LoginPage, "Error fetching user data: ${databaseError.message}", Toast.LENGTH_SHORT).show()
                                }
                            })
                        }
                    } else {
                        // Authentication failed
                        Toast.makeText(this, task.exception?.message, Toast.LENGTH_SHORT).show()
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
        email.setText(sharedPreferences.getString("username", ""))
        password.setText(sharedPreferences.getString("password", ""))
    }
    override fun onBackPressed() {
        finishAffinity()
    }
}