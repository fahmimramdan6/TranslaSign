package com.dicoding.translasign.dictionary

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.dicoding.translasign.R
import com.squareup.picasso.Picasso

class DictionaryScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_dictionary_screen)

        val name = intent.getStringExtra("name")
        val description = intent.getStringExtra("description")
        val photo = intent.getStringExtra("photo")

        val textViewName = findViewById<TextView>(R.id.textTrans)
        val textViewDescription = findViewById<TextView>(R.id.textDescription)
        val imageViewPhoto = findViewById<ImageView>(R.id.imageDictionary)

        textViewName.text = name
        textViewDescription.text = description
        Picasso.get().load(photo).into(imageViewPhoto)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}
