package com.example.v2descuentoapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.v2descuentoapp.databinding.FragmentHomeBinding
import android.graphics.Color
import com.example.v2descuentoapp.R

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private var taxIncluded: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Restaurar el estado guardado
        savedInstanceState?.let {
            taxIncluded = it.getBoolean("taxIncluded", false)
            binding.etListPrice.setText(it.getString("listPrice"))
            binding.etDiscount.setText(it.getString("discount"))
            binding.tvResultDiscountValue.text = it.getString("discountValue")
            binding.tvResultSubtotalValue.text = it.getString("subtotalValue")
            binding.tvResultTaxValue.text = it.getString("taxValue")
            binding.tvTotalLabel.text = it.getString("totalValue")
            updateButtonColors() // Actualizar colores de botones según el estado restaurado
        }

        binding.btnExcludeTax.setOnClickListener {
            taxIncluded = false
            updateButtonColors()
        }

        binding.btnIncludeTax.setOnClickListener {
            taxIncluded = true
            updateButtonColors()
        }

        binding.btnTotalToPay.setOnClickListener {
            calculateTotal()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        // Guardar el estado actual
        outState.apply {
            putBoolean("taxIncluded", taxIncluded)
            putString("listPrice", binding.etListPrice.text.toString())
            putString("discount", binding.etDiscount.text.toString())
            putString("discountValue", binding.tvResultDiscountValue.text.toString())
            putString("subtotalValue", binding.tvResultSubtotalValue.text.toString())
            putString("taxValue", binding.tvResultTaxValue.text.toString())
            putString("totalValue", binding.tvTotalLabel.text.toString())
        }
    }

    private fun updateButtonColors() {
        if (taxIncluded) {
            binding.btnIncludeTax.setBackgroundColor(Color.BLUE)
            binding.btnExcludeTax.setBackgroundColor(Color.GRAY)
        } else {
            binding.btnExcludeTax.setBackgroundColor(Color.BLUE)
            binding.btnIncludeTax.setBackgroundColor(Color.GRAY)
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
            val tax = if (taxIncluded) 0.0 else subtotal * 0.16
            val total = subtotal + tax

            binding.tvResultTaxValue.text = "$${String.format("%.2f", tax)}"
            binding.tvResultDiscountValue.text = "${getString(R.string.title_discount)}: $${String.format("%.2f", discountAmount)}"
            binding.tvResultSubtotalValue.text = "${getString(R.string.list_price)}: $${String.format("%.2f", subtotal)}"
            binding.tvTotalLabel.text = "${getString(R.string.total)}: $${String.format("%.2f", total)}"
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}