package com.example.v2descuentoapp.ui

import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.v2descuentoapp.R

class HistoryActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_dashboard)

        // Cargar el historial desde SharedPreferences
        val sharedPreferences = getSharedPreferences("calculation_history", MODE_PRIVATE)
        val history = sharedPreferences.getStringSet("history", emptySet()) ?: emptySet()

        // Crear un contenedor para los elementos del historial
        val historyContainer = findViewById<LinearLayout>(R.id.historyContainer)

        // Añadir cada cálculo al contenedor
        for (calculation in history) {
            val textView = TextView(this).apply {
                text = calculation
                textSize = 16f
                setPadding(16, 8, 16, 8)
            }
            historyContainer.addView(textView)
        }
    }
}
