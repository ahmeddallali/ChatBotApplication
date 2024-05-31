package com.example.applicationmobile.utils

import com.example.applicationmobile.utils.Constants.OPEN_GOOGLE
import com.example.applicationmobile.utils.Constants.OPEN_MAIN_ACTIVITY2
import com.example.applicationmobile.utils.Constants.OPEN_SEARCH
import java.sql.Date
import java.sql.Timestamp
import java.text.SimpleDateFormat
object BotResponse {

    fun basicResponses(_message: String): String {

        val random = (0..2).random()
        val message = _message.toLowerCase()

        return when {
            message.contains("help") -> {
                """
                🌟 Here are some commands you can try 🌟:
                🎬 - Movie: Afficher les films populaires
                🧮 - solve: Calculateur
                📝 - equation a,b,c: Donner la solution des équations de second degré
                🪙 - flip coin: Pile ou face
                ⏰ - time: Afficher l'heure actuelle
                🌐 - open google: Ouvrir Google
                🔍 - search [terme]: Rechercher sur Internet
                """.trimIndent()
            }

            // Flips a coin
            message.contains("flip") && message.contains("coin") -> {
                val r = (0..1).random()
                val result = if (r == 0) "heads" else "tails"

                "I flipped a coin and it landed on $result"
            }

            // Math calculations
            message.contains("solve") -> {
                val equation: String? = message.substringAfterLast("solve")
                return try {
                    val answer = SolveMath.solveMath(equation ?: "0")
                    "$answer"

                } catch (e: Exception) {
                    "Sorry, I can't solve that."
                }
            }

            // Quadratic equation solving
            message.contains("equation") -> {
                val equation: String? = message.substringAfterLast("equation").trim()

                // Créer un objet Regex pour vérifier le format de l'équation
                val equationRegex = Regex("""^\d+,\d+,\d+$""")

                // Vérifier si l'équation correspond au format correct (a, b, c)
                if (equation != null && equationRegex.matches(equation)) {
                    val coefficients = equation.split(",")

                    // Vérifier si la taille de la liste des coefficients est égale à trois
                    if (coefficients.size == 3) {
                        return try {
                            val a = coefficients[0].trim().toDouble()
                            val b = coefficients[1].trim().toDouble()
                            val c = coefficients[2].trim().toDouble()
                            val answer = SolveEquation.solveQuadraticEquation(a, b, c)
                            answer
                        } catch (e: Exception) {
                            "Sorry, I can't solve that."
                        }
                    } else {
                        "Invalid input. Please provide three coefficients (a, b, c)."
                    }
                } else {
                    "Invalid input format. Please provide coefficients separated by commas (a, b, c)."
                }
            }
            // Hello
            message.contains("hello") -> {
                when (random) {
                    0 -> "Hello there!"
                    1 -> "bonjour"
                    2 -> "Buongiorno!"
                    else -> "error"
                }
            }

            // How are you?
            message.contains("how are you") -> {
                when (random) {
                    0 -> "I'm doing fine, thanks!"
                    1 -> "I'm hungry..."
                    2 -> "Pretty good! How about you?"
                    else -> "error"
                }
            }

            // What time is it?
            message.contains("time") && message.contains("?") -> {
                val timeStamp = Timestamp(System.currentTimeMillis())
                val sdf = SimpleDateFormat("dd/MM/yyyy HH:mm")
                val date = sdf.format(Date(timeStamp.time))

                date.toString()
            }

            // Open Google
            message.contains("open") && message.contains("google") -> {
                OPEN_GOOGLE
            }

            // Search on the internet
            message.contains("search") -> {
                OPEN_SEARCH
            }
            // Open MainActivity2
            message.contains("movie") -> {
                OPEN_MAIN_ACTIVITY2
            }

            // When the program doesn't understand...
            else -> {
                when (random) {
                    0 -> "I don't understand..."
                    1 -> "Try asking me something different"
                    2 -> "Idk"
                    else -> "error"
                }
            }
        }
    }
}