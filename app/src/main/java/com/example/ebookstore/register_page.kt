package com.example.ebookstore

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.example.ebookstore.model.User
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import de.hdodenhof.circleimageview.CircleImageView


class register_page : AppCompatActivity() {
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var registerButton: Button
    private lateinit var loginText:TextView
    private lateinit var email:EditText
    private lateinit var password:EditText
    private lateinit var confirmPassword:EditText
    private lateinit var name:EditText
    private lateinit var db:FirebaseDatabase
    private lateinit var reference: DatabaseReference
    lateinit var databaseReference: DatabaseReference
    private lateinit var storage:FirebaseStorage
    private lateinit var storageReference: StorageReference
    lateinit var circleImage: CircleImageView
    lateinit var linkImg: String

    @SuppressLint("NewApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_register_page)

        supportActionBar?.setBackgroundDrawable(ColorDrawable(ContextCompat.getColor(this, R.color.purple_200)))
        window?.statusBarColor = ContextCompat.getColor(this, R.color.status)

        registerButton = findViewById(R.id.registerButton)
        firebaseAuth = FirebaseAuth.getInstance()
        db = FirebaseDatabase.getInstance()
        circleImage = findViewById(R.id.imageView3)
        linkImg=""
        circleImage.setOnClickListener{
            intent = Intent()
            intent.setType("image/*")
            intent.setAction(Intent.ACTION_GET_CONTENT)
            startActivityForResult(Intent.createChooser(intent,"Choose the image"),1)

        }

        loginText = findViewById(R.id.loginText)

        email = findViewById(R.id.emailId)
        password= findViewById(R.id.registerpassword)
        confirmPassword=findViewById(R.id.confirmpassword)
        name = findViewById(R.id.Name)


        registerButton.setOnClickListener{

            val em = email.text.toString()
            val pass = password.text.toString()
            var nam = name.text.toString()
            var cpass = confirmPassword.text.toString()

            if(em.isNotEmpty() && pass.isNotEmpty() && nam.isNotEmpty()  && cpass.isNotEmpty() && linkImg.isNotEmpty()) {

                firebaseAuth.createUserWithEmailAndPassword(em, pass).addOnCompleteListener {

                    if (it.isSuccessful) {
                        Toast.makeText(this, "User created", Toast.LENGTH_SHORT).show()

                        realtimeDatabase(nam,em,pass)

                        var i: Intent = Intent(this, LoginPage::class.java)
                        startActivity(i)
                    } else {
                        try {
                            throw it.getException()!!
                        }
                        catch( e: FirebaseAuthInvalidCredentialsException) {
                            Toast.makeText(this,"Invalid Credentials Check your email",Toast.LENGTH_SHORT).show()
                        }catch (e: FirebaseAuthWeakPasswordException) {
                            Toast.makeText(this, "Weak Paasword", Toast.LENGTH_SHORT).show()
                        } catch (e: FirebaseAuthUserCollisionException) {
                            Toast.makeText(this, "Some error! Please try again"+e.toString(), Toast.LENGTH_SHORT).show()
                        } catch (e: Exception) {

                            Toast.makeText(
                                this,
                                it.exception.toString(),
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                    }
                }
            }
            else {
                Toast.makeText(this,"Empty Fields are not allowed",Toast.LENGTH_SHORT).show()
            }


        }
        loginText.setOnClickListener{
            val intent :Intent = Intent(this,LoginPage::class.java)
            startActivity(intent)
        }

    }
    fun realtimeDatabase(nam: String, em: String, pass: String) {
        Toast.makeText(this, linkImg, Toast.LENGTH_LONG).show()
        val users = User(nam, em, pass, linkImg)
        val sanitizedEm = em.replace(".", "_").replace("#", "_").replace("$", "_").replace("[", "_").replace("]", "_")
        val reference = FirebaseDatabase.getInstance().getReference("Users").child(sanitizedEm)
        reference.setValue(users).addOnCompleteListener { task ->
            try {
                if (task.isSuccessful) {
                    Toast.makeText(this, "Your data saved", Toast.LENGTH_SHORT).show()
                } else {
                    throw task.exception ?: Exception("Unknown error")
                }
            } catch (e: Exception) {
                if (e is FirebaseException) {
                    // Handle Firebase-related exceptions
                    Toast.makeText(this, "Firebase Error: ${e.message}", Toast.LENGTH_SHORT).show()
                } else {
                    // Handle other exceptions
                    Toast.makeText(this, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode==1 && data!=null && resultCode== RESULT_OK && data.getData()!=null){
            Glide.with(this).load(data.data).into(circleImage)
            onImageAdd(data.data)
        }
    }

    fun onImageAdd(imageUri: Uri?) {
        if (imageUri != null) {
            // Set the desired filename for the uploaded image
            storageReference =
                FirebaseStorage.getInstance().reference.child("Images/"+System.currentTimeMillis()+".jpg")

            storageReference.putFile(imageUri)
                .addOnSuccessListener { taskSnapshot: UploadTask.TaskSnapshot? ->
                    storageReference.downloadUrl
                        .addOnSuccessListener { downloadUrl: Uri ->
                            linkImg = downloadUrl.toString()
                        }
                        .addOnFailureListener { e: Exception? ->
                        }
                }
                .addOnFailureListener { e: Exception? ->
                    // Handle any errors if the image upload fails
                }
        }
    }

}