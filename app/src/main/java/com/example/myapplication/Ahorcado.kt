package com.example.myapplication

class Ahorcado(private val vista: IAhorcado, private val palabraAAdivinar: String) {
    var intentosRestantes = 6
    private val letrasReveladas = BooleanArray(palabraAAdivinar.length) { false }

    fun jugar(letra: Char) {
        if (!palabraAAdivinar.contains(letra, true)) {
            intentosRestantes--
        } else {
            for (i in palabraAAdivinar.indices) {
                if (palabraAAdivinar[i].equals(letra, true)) {
                    letrasReveladas[i] = true
                    vista.mostrarLetraRevelada(letra)
                }
            }
        }

        vista.mostrarPalabra(obtenerPalabraOculta())
        vista.mostrarIntentosRestantes(intentosRestantes)

        if (intentosRestantes == 0) {
            vista.mostrarMensajeFinJuego("Perdiste. La palabra era $palabraAAdivinar.")
        } else if (palabraAAdivinar.equals(obtenerPalabraOculta(), true)) {
            vista.mostrarMensajeFinJuego("¡Ganaste!")
        }
    }

    fun obtenerPalabraOculta(): String {
        return palabraAAdivinar.mapIndexed { i, c ->
            if (letrasReveladas[i]) c else '-'
        }.joinToString("")
    }
}
