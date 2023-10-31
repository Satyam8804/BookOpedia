package com.example.ebookstore

import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask


class book_add : AppCompatActivity() {
    lateinit var firebaseAuth: FirebaseAuth
    lateinit var databaseReference:DatabaseReference
    lateinit var storageReference: StorageReference
    lateinit var title:EditText
    lateinit var year :EditText
    lateinit var author:EditText
    lateinit var description:EditText
    lateinit var genre:EditText
    lateinit var uploadImage:Button
    lateinit var uploadPdf:Button
    lateinit var addBook:Button
    lateinit var imgPicker:ActivityResultLauncher<String>
    lateinit var pdfPicker: ActivityResultLauncher<String>
    lateinit var linkPdf :String
    lateinit var linkImg :String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book_add)
        title = findViewById(R.id.title)
        author = findViewById(R.id.Author)
        description=findViewById(R.id.Description)
        genre=findViewById(R.id.Genre)
        uploadImage=findViewById(R.id.UploadImage)
        uploadPdf=findViewById(R.id.UploadPDF)
        addBook = findViewById(R.id.AddBook)
        year = findViewById(R.id.publishYear)

        val pubYear = year.text.toString()

        imgPicker=registerForActivityResult(ActivityResultContracts.GetContent()){imageUri->
            onImageAdd(imageUri)
        }
        pdfPicker = registerForActivityResult(ActivityResultContracts.GetContent()){ pdfUri ->
            uploadPdfToFirebase(pdfUri!!)
        }
        uploadImage.setOnClickListener{
            imgPicker.launch("image/")
        }
        uploadPdf.setOnClickListener {
            pdfPicker.launch("application/pdf")
        }
        addBook.setOnClickListener {
           validateData(title,author,description,genre,linkImg,linkPdf,pubYear)
        }




    }

    private fun uploadPdfToFirebase(pdfUri: Uri?) {
        if (pdfUri != null) {
             // Set the desired filename for the uploaded PDF
            val storageRef = FirebaseStorage.getInstance().reference.child("Uploads/"+title+".pdf")
            storageRef.putFile(pdfUri)
                .addOnSuccessListener { taskSnapshot: UploadTask.TaskSnapshot? ->
                    // PDF upload successful
                    // Get the download URL of the uploaded PDF
                    storageRef.downloadUrl
                        .addOnSuccessListener { downloadUrl: Uri ->
                            linkPdf = downloadUrl.toString()
                        }.addOnFailureListener { e: Exception? -> }
                }
                .addOnFailureListener { e: Exception? -> }
        }
    }
     fun validateData(title: EditText, author: EditText, description: EditText, genre: EditText, linkImg: String, linkPdf: String, pubYear: String) {
        val bookTitle = title.text.toString().trim()
        val bookAuthor = author.text.toString().trim()
        val bookDescription = description.text.toString().trim()
        val bookGenre = genre.text.toString().trim()

        if (bookTitle.isEmpty() || bookAuthor.isEmpty() || bookDescription.isEmpty() || bookGenre.isEmpty() || linkImg.isEmpty() || linkPdf.isEmpty() || pubYear.isEmpty()) {
          Toast.makeText(this,"Please Enter all the fields",Toast.LENGTH_LONG).show()
            return
        } else {
            val model:bookModel = bookModel(title.toString(),linkImg,description.toString(), "4.3",genre.toString() , author.toString(), pubYear,linkPdf)
            realtimeDatabase(model,genre.toString());
        }
    }

    private fun realtimeDatabase(bookModel:  bookModel, genre: String) {
        // You should have a reference to your Firebase Realtime Database.
        // You can initialize it like this:
         val databaseReference = FirebaseDatabase.getInstance().reference

        // Generate a unique key for the new book entry

        val newBookKey = databaseReference.child("books").child(genre).push().key

        // Create a map with the book data
        val bookData = hashMapOf(
            "title" to bookModel.title,
            "coverImg" to bookModel.coverImg,
            "des" to bookModel.des,
            "rating" to bookModel.rating,
            "genre" to bookModel.genre,
            "authorName" to bookModel.authorName,
            "publishedYear" to bookModel.publishedYear,
            "link" to bookModel.link
        )

        if (newBookKey != null) {
            // Use the unique key to add the book data to the database under the specified genre
            databaseReference.child("books").child(genre).child(newBookKey).setValue(bookData)
                .addOnSuccessListener {
                    // Book data added successfully
                    // You can show a success message or navigate to another screen.
                }
                .addOnFailureListener {
                    // Failed to add book data to the database
                    // Handle the error or show an error message to the user.
                }
        }
    }



    private fun onImageAdd(imageUri: Uri?) {
        if (imageUri != null) {
            // Set the desired filename for the uploaded image
            val storageRef =
                FirebaseStorage.getInstance().reference.child("Uploads/${title.text}.jpg")

            storageRef.putFile(imageUri)
                .addOnSuccessListener { taskSnapshot: UploadTask.TaskSnapshot? ->
                    // Image upload successful
                    // Get the download URL of the uploaded image
                    storageRef.downloadUrl
                        .addOnSuccessListener { downloadUrl: Uri ->
                            linkImg = downloadUrl.toString()
                        }
                        .addOnFailureListener { e: Exception? ->
                            // Handle any errors if the download URL retrieval fails
                        }
                }
                .addOnFailureListener { e: Exception? ->
                    // Handle any errors if the image upload fails
                }
        }
    }


    }


