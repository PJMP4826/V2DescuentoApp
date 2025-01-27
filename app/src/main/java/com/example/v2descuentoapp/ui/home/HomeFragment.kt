package com.example.v2descuentoapp.ui.home

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.v2descuentoapp.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnExcludeTax.setOnClickListener {
            calculateDiscount(false)
        }

        binding.btnIncludeTax.setOnClickListener {
            calculateDiscount(true)
        }

        binding.btnTotalToPay.setOnClickListener {
            calculateTotal()
        }
    }

    private fun calculateDiscount(includeTax: Boolean) {
        val listPriceText = binding.etListPrice.text.toString()
        val discountText = binding.etDiscount.text.toString()

        if (listPriceText.isNotEmpty() && discountText.isNotEmpty()) {
            val listPrice = listPriceText.toDouble()
            val discount = discountText.toDouble()

            val discountAmount = (listPrice * discount) / 100
            val subtotal = listPrice - discountAmount
            val tax = if (includeTax) subtotal * 0.16 else 0.0
            val total = subtotal + tax

            binding.tvResultDiscountValue.text = "$${String.format("%.2f", discountAmount)}"
            binding.tvResultSubtotalValue.text = "$${String.format("%.2f", subtotal)}"
            binding.tvResultTaxValue.text = "$${String.format("%.2f", tax)}"
            binding.tvTotalLabel.text = "$${String.format("%.2f", total)}"

            // Guardar el cálculo en el historial
            saveCalculationToHistory("Precio de lista: $listPrice, Descuento: $discount%, Total: $total")
        }
    }

    private fun calculateTotal() {
        val listPriceText = binding.etListPrice.text.toString()
        val discountText = binding.etDiscount.text.toString()

        if (listPriceText.isNotEmpty() && discountText.isNotEmpty()) {
            val listPrice = listPriceText.toDouble()
            val discount = discountText.toDouble()

            val discountAmount = (listPrice * discount) / 100
            val subtotal = listPrice - discountAmount
            val tax = subtotal * 0.16
            val total = subtotal + tax

            binding.tvTotalLabel.text = "$${String.format("%.2f", total)}"

            // Guardar el cálculo en el historial
            saveCalculationToHistory("Precio de lista: $listPrice, Descuento: $discount%, Total: $total")
        }
    }

    private fun saveCalculationToHistory(calculation: String) {
        val sharedPreferences = requireContext().getSharedPreferences("calculation_history", Context.MODE_PRIVATE)
        val history = sharedPreferences.getStringSet("history", emptySet()) ?: emptySet()
        val updatedHistory = history.toMutableSet().apply { add(calculation) }
        sharedPreferences.edit().putStringSet("history", updatedHistory).apply()
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
