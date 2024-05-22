package com.example.applicationmobile

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.applicationmobile.databinding.ActivityDeuxiemeBinding
import com.example.applicationmobile.databinding.ActivityMainBinding
import com.example.applicationmobile.models.Movie
import com.example.applicationmobile.models.MovieResponse
import com.example.applicationmobile.services.MovieApiInterface
import com.example.applicationmobile.services.MovieApiService
import com.google.firebase.auth.FirebaseAuth

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity2 : ComponentActivity() {

    private lateinit var binding: ActivityDeuxiemeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDeuxiemeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rvMoviesList.layoutManager = LinearLayoutManager(this)
        binding.rvMoviesList.setHasFixedSize(true)

        getMovieData { movies: List<Movie> ->
            // Modifier votre code pour la logique de clic
            binding.rvMoviesList.adapter = MovieAdapter(movies) { selectedMovie ->
                // Logique à exécuter lorsque vous cliquez sur un film
                sendEmail(selectedMovie)
            }
        }
    }
    private fun sendEmail(movie: Movie) {
        val subject = "Détails du film : ${movie.title}"
        val message = "Titre : ${movie.title}\nDate de sortie : ${movie.release}\nDescription : ${movie.title}"

        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "plain/text"
        intent.putExtra(Intent.EXTRA_SUBJECT, subject)
        intent.putExtra(Intent.EXTRA_TEXT, message)
        startActivity(Intent.createChooser(intent, "Choisir une application pour envoyer l'e-mail"))
    }

    private fun getMovieData(callback: (List<Movie>) -> Unit) {
        val apiService = MovieApiService.getInstance().create(MovieApiInterface::class.java)
        apiService.getMovieList().enqueue(object : Callback<MovieResponse> {
            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                // Gérer l'échec de la récupération des données
            }

            override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
                // Appeler la fonction de rappel avec la liste de films récupérée
                callback(response.body()?.movies ?: emptyList())
            }
        })
    }
}