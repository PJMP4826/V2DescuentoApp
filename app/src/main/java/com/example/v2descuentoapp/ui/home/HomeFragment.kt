package com.example.v2descuentoapp.ui.home

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.v2descuentoapp.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val viewModel: HomeViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Restaurar estado guardado si existe
        savedInstanceState?.let { bundle ->
            viewModel.updateValues(
                bundle.getString("listPrice", ""),
                bundle.getString("discount", ""),
                bundle.getString("discountValue", "$0.00"),
                bundle.getString("subtotalValue", "$0.00"),
                bundle.getString("taxValue", "$0.00"),
                bundle.getString("totalValue", "$0.00")
            )
            viewModel.setTaxIncluded(bundle.getBoolean("taxIncluded", false))
        }

        // Observar cambios y actualizar la UI automáticamente
        viewModel.listPrice.observe(viewLifecycleOwner) { binding.etListPrice.setText(it) }
        viewModel.discount.observe(viewLifecycleOwner) { binding.etDiscount.setText(it) }
        viewModel.discountValue.observe(viewLifecycleOwner) { binding.tvResultDiscountValue.text = it }
        viewModel.subtotalValue.observe(viewLifecycleOwner) { binding.tvResultSubtotalValue.text = it }
        viewModel.taxValue.observe(viewLifecycleOwner) { binding.tvResultTaxValue.text = it }
        viewModel.totalValue.observe(viewLifecycleOwner) { binding.tvTotalLabel.text = it }
        viewModel.taxIncluded.observe(viewLifecycleOwner) { updateButtonColors(it) }

        binding.btnExcludeTax.setOnClickListener { viewModel.setTaxIncluded(false) }
        binding.btnIncludeTax.setOnClickListener { viewModel.setTaxIncluded(true) }
        binding.btnTotalToPay.setOnClickListener { calculateTotal() }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.apply {
            putString("listPrice", binding.etListPrice.text.toString())
            putString("discount", binding.etDiscount.text.toString())
            putString("discountValue", binding.tvResultDiscountValue.text.toString())
            putString("subtotalValue", binding.tvResultSubtotalValue.text.toString())
            putString("taxValue", binding.tvResultTaxValue.text.toString())
            putString("totalValue", binding.tvTotalLabel.text.toString())
            putBoolean("taxIncluded", viewModel.taxIncluded.value ?: false)
        }
    }

    private fun updateButtonColors(taxIncluded: Boolean) {
        binding.btnIncludeTax.setBackgroundColor(if (taxIncluded) Color.BLUE else Color.GRAY)
        binding.btnExcludeTax.setBackgroundColor(if (taxIncluded) Color.GRAY else Color.BLUE)
    }

    private fun calculateTotal() {
        val listPriceText = binding.etListPrice.text.toString()
        val discountText = binding.etDiscount.text.toString()

        if (listPriceText.isNotEmpty() && discountText.isNotEmpty()) {
            val listPrice = listPriceText.toDouble()
            val discount = discountText.toDouble()

            val discountAmount = (listPrice * discount) / 100
            val subtotal = listPrice - discountAmount
            val tax = if (viewModel.taxIncluded.value == true) 0.0 else subtotal * 0.16
            val total = subtotal + tax

            // Guardar valores en el ViewModel
            viewModel.updateValues(
                listPriceText,
                discountText,
                "$${String.format("%.2f", discountAmount)}",
                "$${String.format("%.2f", subtotal)}",
                "$${String.format("%.2f", tax)}",
                "$${String.format("%.2f", total)}"
            )

            // Guardar el cálculo en SharedPreferences
            saveCalculationToHistory(
                "Precio: $${String.format("%.2f", listPrice)}, " +
                        "Descuento: $discount%, " +
                        "Total: $${String.format("%.2f", total)}"
            )
        }
    }

    private fun saveCalculationToHistory(calculation: String) {
        val sharedPreferences = requireContext().getSharedPreferences(
            "calculation_history",
            Context.MODE_PRIVATE
        )
        val history = sharedPreferences.getStringSet("history", mutableSetOf()) ?: mutableSetOf()
        val updatedHistory = history.toMutableSet().apply { add(calculation) }
        sharedPreferences.edit().putStringSet("history", updatedHistory).apply()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}