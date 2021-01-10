package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class LoginActivity: AppCompatActivity() {
    var nextButton: Button? = null;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        nextButton = findViewById(R.id.next_button)

        nextButton?.setOnClickListener {
            var clickwrkexp = Intent(this@LoginActivity, MainActivity::class.java)
            startActivity(clickwrkexp)
        }


    }
}