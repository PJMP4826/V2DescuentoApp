package com.example.v2descuentoapp.ui

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.v2descuentoapp.databinding.FragmentDashboardBinding

class HistoryActivity : AppCompatActivity() {

    private lateinit var binding: FragmentDashboardBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Cargar el historial desde SharedPreferences
        val sharedPreferences = getSharedPreferences("calculation_history", MODE_PRIVATE)
        val history = sharedPreferences.getStringSet("history", emptySet()) ?: emptySet()

        // Añadimos los cálculo al contenedor
        for (calculation in history) {
            val textView = TextView(this).apply {
                text = calculation
                textSize = 16f
                setPadding(16, 8, 16, 8)
            }
            binding.historyContainer.addView(textView)
        }
    }
}