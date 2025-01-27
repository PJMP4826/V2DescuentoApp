package com.example.v2descuentoapp.ui.dashboard

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.v2descuentoapp.R

class DashboardFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_dashboard, container, false)

        // Cargar el historial desde SharedPreferences
        val sharedPreferences = requireContext().getSharedPreferences("calculation_history", Context.MODE_PRIVATE)
        val history = sharedPreferences.getStringSet("history", emptySet()) ?: emptySet()

        // Crear un contenedor para los elementos del historial
        val historyContainer = root.findViewById<LinearLayout>(R.id.historyContainer)

        // Añadir cada cálculo al contenedor
        for (calculation in history) {
            val textView = TextView(requireContext()).apply {
                text = calculation
                textSize = 16f
                setPadding(16, 8, 16, 8)
            }
            historyContainer.addView(textView)
        }

        return root
    }
}
