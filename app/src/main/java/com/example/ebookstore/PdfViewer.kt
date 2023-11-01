package com.example.ebookstore

import android.graphics.drawable.ColorDrawable
import android.os.AsyncTask
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import com.github.barteksc.pdfviewer.PDFView
import java.io.BufferedInputStream
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL
import java.io.IOException

class PdfViewer : AppCompatActivity() {
    private lateinit var pdfView: PDFView

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pdf_viewer)
        supportActionBar?.setBackgroundDrawable(ColorDrawable(ContextCompat.getColor(this, R.color.purple_200)))
        window?.statusBarColor = ContextCompat.getColor(this, R.color.purple_200)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        title = intent.getStringExtra("bookTitle")

        pdfView = findViewById(R.id.pdfView)

        val pdfUrl = intent.getStringExtra("pdfLink")
        // Load the PDF from the URL
        PdfLoader().execute(pdfUrl)
    }

    private inner class PdfLoader : AsyncTask<String, Void, InputStream>() {
        override fun doInBackground(vararg params: String?): InputStream? {
            var inputStream: InputStream? = null
            try {
                val url: URL = URL(params[0])
                val connection = url.openConnection() as HttpURLConnection
                if (connection.responseCode == 200) {
                    inputStream = BufferedInputStream(connection.inputStream)
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }
            return inputStream
        }

        override fun onPostExecute(result: InputStream?) {
            if (result != null) {
                // Load the PDF using the PDFView library
                pdfView.fromStream(result)
                    .enableSwipe(true)
                    .swipeHorizontal(false)
                    .enableDoubletap(true)
                    .defaultPage(0)

                    .enableAnnotationRendering(false)
                    .password(null)
                    .scrollHandle(null)
                    .enableAntialiasing(true)

                    .spacing(0)
                    .autoSpacing(false)

                    .fitEachPage(true)
                    .pageSnap(false)
                    .pageFling(false)
                    .nightMode(false)
                    .load();
            } else {
                // Handle the case where PDF loading failed
                Log.e("PdfLoader", "Failed to load PDF")
            }
        }
    }
}
