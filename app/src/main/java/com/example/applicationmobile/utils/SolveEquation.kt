package com.example.applicationmobile.utils

object SolveEquation {
    fun solveQuadraticEquation(a: Double, b: Double, c: Double): String {
        val discriminant = b * b - 4 * a * c
        val root1: Double
        val root2: Double

        if (discriminant > 0) {
            root1 = (-b + Math.sqrt(discriminant)) / (2 * a)
            root2 = (-b - Math.sqrt(discriminant)) / (2 * a)
            return "Root 1: $root1, Root 2: $root2"
        } else if (discriminant == 0.0) {
            root1 = -b / (2 * a)
            return "Double root: $root1"
        } else {
            val realPart = -b / (2 * a)
            val imaginaryPart = Math.sqrt(-discriminant) / (2 * a)
            return "Complex roots: $realPart + $imaginaryPart i and $realPart - $imaginaryPart i"
        }
    }

    // Fonction pour résoudre une équation quadratique avec les coefficients fournis
    fun solveQuadraticEquationWithCoefficients(a: Double, b: Double, c: Double): String {
        // Appel de la fonction solveQuadraticEquation avec les coefficients fournis
        return solveQuadraticEquation(a, b, c)
    }
}