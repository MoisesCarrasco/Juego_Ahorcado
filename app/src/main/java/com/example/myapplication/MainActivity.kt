package com.example.myapplication


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button


class MainActivity : AppCompatActivity() {
    private lateinit var botonCmz:Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        botonCmz= findViewById(R.id.btnCmz)
        botonCmz.setOnClickListener {
            // Crear Intent para la nueva actividad
            val intent = Intent(this, JuegoAhorcado::class.java)

            // Llamar a la nueva actividad
            startActivity(intent)
        }
    }
}