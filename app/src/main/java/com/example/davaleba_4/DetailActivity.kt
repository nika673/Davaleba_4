package com.example.davaleba_4

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class DetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)


        val textView = findViewById<TextView>(R.id.detailText)
        val result = intent.getStringExtra("text")
        textView.text = result
    }

}