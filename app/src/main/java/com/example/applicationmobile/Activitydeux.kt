package com.example.applicationmobile

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.ComponentActivity
import com.example.applicationmobile.databinding.ActivityActivitydeuxBinding
import com.example.applicationmobile.databinding.ActivityDeuxiemeBinding
import com.google.firebase.auth.FirebaseAuth

class Activitydeux : ComponentActivity()  {
    private lateinit var binding: ActivityActivitydeuxBinding
    private lateinit var user: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityActivitydeuxBinding.inflate(layoutInflater)
        setContentView(binding.root)
        user = FirebaseAuth.getInstance()
        binding.imageView.setImageResource(R.drawable.e)

        if(user.currentUser != null){
            user.currentUser?.let {
                binding.tvUserEmail.text = it.email
            }
        }

        binding.btnSignout.setOnClickListener {
            user.signOut()
            startActivity(Intent(
                this,MainActivity::class.java
            ))
            finish()
        }
        binding.movie.setOnClickListener {
            startActivity(Intent(
                this,MainActivity2::class.java
            ))
            finish()
        }
        binding.chat.setOnClickListener {
            startActivity(Intent(
                this,ActivityChat::class.java
            ))
            finish()
        }
    }
}