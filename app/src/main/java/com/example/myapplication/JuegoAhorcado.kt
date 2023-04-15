package com.example.myapplication

import android.annotation.SuppressLint
import android.content.Intent
import android.content.res.Resources
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.text.InputType
import android.widget.*
import androidx.appcompat.app.AlertDialog

class JuegoAhorcado : AppCompatActivity(),IAhorcado {
    private lateinit var juego: Ahorcado
    private lateinit var palabraTextView: TextView
    private lateinit var intentosTextView: TextView

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_juego_ahorcado)

        val palabraAAdivinar = intent.getStringExtra("palabraAAdivinar") ?: "Kotlin"

        juego = Ahorcado(this, palabraAAdivinar)

        palabraTextView = findViewById(R.id.txtPalabra)
        intentosTextView = findViewById(R.id.txtIntentos)
        intentosTextView.setTextColor(Color.RED)
        val tecladoGrid: GridLayout = findViewById(R.id.tecladoGrid)
        val jugarButton: Button = findViewById(R.id.btnIngresar)

        jugarButton.setOnClickListener {
            mostrarDialogoNuevaPalabra()
        }
        for (i in 'A'..'Z') {
            val boton = Button(this)
            boton.text = i.toString()
            boton.setOnClickListener { juego.jugar(i) }
            val params = GridLayout.LayoutParams(
                GridLayout.spec(GridLayout.UNDEFINED, 1f),
                GridLayout.spec(GridLayout.UNDEFINED, 1f)
            )
            params.width = 30.dpToPx()
            params.height = 40.dpToPx()
            params.leftMargin= 0.dpToPx()
            boton.layoutParams = params
            tecladoGrid.addView(boton)
        }


        mostrarPalabra(juego.obtenerPalabraOculta())
        mostrarIntentosRestantes(juego.intentosRestantes)
    }

    override fun mostrarPalabra(palabra: String) {
        palabraTextView.text = palabra
    }

    override fun mostrarIntentosRestantes(intentos: Int) {
        intentosTextView.text = "$intentos"
        intentosTextView.setTextColor(Color.RED)

        Handler().postDelayed({
            intentosTextView.setTextColor(Color.BLACK)
        }, 1000) // Cambia el color de vuelta a negro después de 1 segundo (1000ms)

    }


    override fun mostrarLetraRevelada(letra: Char) {
        Toast.makeText(this, "¡Letra $letra revelada!", Toast.LENGTH_SHORT).show()
    }

    override fun mostrarMensajeFinJuego(mensaje: String) {
        AlertDialog.Builder(this)
            .setMessage(mensaje)
            .setCancelable(false)
            .setPositiveButton("Volver a jugar") { _, _ ->
                startActivity(Intent(this, JuegoAhorcado::class.java))
                finish()
            }
            .show()
    }
    private fun mostrarDialogoNuevaPalabra() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Adivina la palabra")
        val input = EditText(this)
        input.inputType = InputType.TYPE_CLASS_TEXT
        builder.setView(input)
        builder.setPositiveButton("Jugar") { dialog, _ ->
            val intent = Intent(this, JuegoAhorcado::class.java)
            intent.putExtra("palabraAAdivinar", input.text.toString().toUpperCase())
            startActivity(intent)
            dialog.dismiss()
        }
        builder.setNegativeButton("Cancelar") { dialog, _ ->
            dialog.cancel()
        }
        builder.show()
    }
    fun Int.dpToPx(): Int = (this * Resources.getSystem().displayMetrics.density).toInt()

}