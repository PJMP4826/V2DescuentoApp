package com.example.v2descuentoapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.v2descuentoapp.databinding.FragmentHomeBinding
import android.graphics.Color

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

            binding.tvResultDiscountValue.text = "$${String.format("%.2f", discountAmount)}"
            binding.tvResultSubtotalValue.text = "$${String.format("%.2f", subtotal)}"
            binding.tvResultTaxValue.text = "$${String.format("%.2f", tax)}"
            binding.tvTotalLabel.text = "$${String.format("%.2f", total)}"
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
