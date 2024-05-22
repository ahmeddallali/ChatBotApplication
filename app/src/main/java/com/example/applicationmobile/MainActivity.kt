package com.example.applicationmobile

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.applicationmobile.databinding.ActivityMainBinding
import com.example.applicationmobile.ui.theme.ApplicationmobileTheme
import com.google.firebase.auth.FirebaseAuth

class MainActivity : ComponentActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var user: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ApplicationmobileTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    Greeting("Android")
                }
            }
        }
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        user = FirebaseAuth.getInstance()

        checkIfUserIsLogged()


        binding.btnLogin.setOnClickListener {
            registerUser()
        }
    }
    private fun checkIfUserIsLogged(){
        if (user.currentUser != null){
            startActivity(Intent(this,Activitydeux::class.java))
            finish()
        }
    }
    private fun registerUser(){
        val email = binding.etEmail.text.toString()
        val password  = binding.etPassword.text.toString()
        if (email.isNotEmpty() && password.isNotEmpty()){
            user.createUserWithEmailAndPassword(email,password).addOnCompleteListener(MainActivity()){task ->
                if (task.isSuccessful){
                    Toast.makeText(this,"User added successfully ", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this,Activitydeux::class.java))
                    finish()

                }else{
                    user.signInWithEmailAndPassword(email,password).addOnCompleteListener { mTask ->
                        if (mTask.isSuccessful){
                            startActivity(Intent(this,Activitydeux::class.java))
                            finish()


                        }else{
                            Toast.makeText(this,task.exception!!.message, Toast.LENGTH_SHORT).show()


                        }

                    }

                }
            }
        }else{
            Toast.makeText(this,"Email and passsword can not be empty ", Toast.LENGTH_SHORT).show()
        }
    }


}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
            text = "Hello $name!",
            modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ApplicationmobileTheme {
        Greeting("Android")
    }
}