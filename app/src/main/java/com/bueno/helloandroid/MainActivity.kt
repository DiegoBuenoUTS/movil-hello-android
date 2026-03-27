package com.bueno.helloandroid

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Referenciar elementos de la interfaz
        val textView = findViewById<TextView>(R.id.textView)
        val btnSaludar = findViewById<Button>(R.id.btnSaludar)

        // Variable contador
        var contador = 0

        // Asignar acción al botón
        btnSaludar.setOnClickListener {
            contador++
            textView.text = "Has hecho clic $contador veces"

            // Mostrar mensaje emergente
            Toast.makeText(
                this,
                "¡Botón presionado!",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}