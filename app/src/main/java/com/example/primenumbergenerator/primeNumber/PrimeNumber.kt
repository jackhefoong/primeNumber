package com.example.primenumbergenerator.primeNumber

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.whenStarted
import androidx.navigation.fragment.NavHostFragment
import com.example.primenumbergenerator.databinding.FragmentPrimeNumberBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class primeNumber : Fragment() {

    lateinit var binding: FragmentPrimeNumberBinding
    private val viewModel: PrimeNumberViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPrimeNumberBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val args = arguments
        val start = args?.getInt("start")
        val end = args?.getInt("end")

        binding.primeNumbers.setText("Prime Numbers from " + start + " to " + end)

        lifecycleScope.launch {
            whenStarted {
                binding.loading.visibility = View.VISIBLE
                val primeNumbers = withContext(Dispatchers.Default) {
                    viewModel.generatePrimeNumbers(start!!, end!!)
                }
                binding.loading.visibility = View.GONE

                val hasPrimeNumber = primeNumbers.size
                if(hasPrimeNumber == 0) {
                    binding.generatedText.append("No prime numbers found")
                }
                for (num in primeNumbers) {
                    binding.generatedText.append("$num  ")
                }
            }

        }

        binding.backButton.setOnClickListener {
            NavHostFragment.findNavController(this).popBackStack()
        }

    }
}