package com.example.v2descuentoapp.ui

import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.v2descuentoapp.databinding.ActivityHistoryBinding

class HistoryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHistoryBinding
    private var historyList: MutableSet<String> = mutableSetOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Restaurar historial al rotar pantalla
        historyList = savedInstanceState?.getStringArray("history")?.toMutableSet() ?: loadHistory()

        // Mostrar historial en pantalla
        displayHistory()
    }

    private fun loadHistory(): MutableSet<String> {
        val sharedPreferences = getSharedPreferences("calculation_history", MODE_PRIVATE)
        return sharedPreferences.getStringSet("history", emptySet())?.toMutableSet() ?: mutableSetOf()
    }

    private fun displayHistory() {
        val historyContainer: LinearLayout? = binding.historyContainer // Verificar si existe
        historyContainer?.removeAllViews()

        for (calculation in historyList) {
            val textView = TextView(this).apply {
                text = calculation
                textSize = 16f
                setPadding(16, 8, 16, 8)
            }
            historyContainer?.addView(textView)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putStringArray("history", historyList.toTypedArray()) // Guardar historial al rotar
    }
}
