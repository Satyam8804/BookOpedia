package com.example.ebookstore
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.ebookstore.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class LoginPage : AppCompatActivity() {

    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var password: EditText
    private lateinit var register: TextView
    private lateinit var loginButton: Button
    private lateinit var email: EditText
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
            val userEmail = email.text.toString()
            val pass = password.text.toString()

            if (userEmail.isNotEmpty() && pass.isNotEmpty()) {
                firebaseAuth.signInWithEmailAndPassword(userEmail, pass)
                    .addOnCompleteListener { loginTask ->
                        try {
                            if (loginTask.isSuccessful) {
                                // Successfully logged in, fetch user data from the database
                                fetchUserDataFromDatabase(userEmail, pass)
                            } else {
                                throw loginTask.exception ?: Exception("Unknown error")
                            }
                        } catch (e: Exception) {
                            handleLoginError(e)
                        }
                    }
            }
        }

        register.setOnClickListener {
            val intent: Intent = Intent(this, register_page::class.java)
            startActivity(intent)
        }

        sharedPreferences = getSharedPreferences("userData", Context.MODE_PRIVATE)
        email.setText(sharedPreferences.getString("username", ""))
        password.setText(sharedPreferences.getString("password", ""))
    }

    private fun fetchUserDataFromDatabase(userEmail: String, pass: String) {
        val sanitizedEm = userEmail.replace(".", "_").replace("#", "_").replace("$", "_").replace("[", "_").replace("]", "_")
        val reference = FirebaseDatabase.getInstance().getReference("Users").child(sanitizedEm)

        reference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    val user = dataSnapshot.getValue(User::class.java)
                    if (user != null) {
                        val userName = user.name
                        val userImage = user.image
                        Log.d("User Data", "User Name: $userName, User Image: $userImage")

                        // ... rest of your code ...
                        // Store user data in SharedPreferences
                        val editor = sharedPreferences.edit()
                        editor.putString("username", userEmail)
                        editor.putString("password", pass)
                        editor.putString("userName", userName)
                        editor.putString("userImage", userImage)
                        editor.apply()

                        // Start the MainActivity and pass the user data
                        val intent = Intent(applicationContext, MainActivity::class.java)
                        intent.putExtra("userName", userName)
                        intent.putExtra("userImage", userImage)
                        startActivity(intent)
                    } else {
                        Log.e("User Data", "Failed to deserialize user data")
                    }
                } else {
                    Log.e("User Data", "DataSnapshot does not exist")
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.e("Database Error", "Error: ${databaseError.message}")
                handleLoginError(Exception("Database Error: ${databaseError.message}"))
            }
        })

    }
    private fun handleLoginError(exception: Exception) {
        runOnUiThread {
            Toast.makeText(this, "Login Error: ${exception.message}", Toast.LENGTH_SHORT).show()
        }
    }
}
