package com.example.primenumbergenerator.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.NavHostFragment
import com.example.primenumbergenerator.databinding.FragmentHomeBinding

class home : Fragment() {
    lateinit var binding : FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.generate.setOnClickListener {
            val start = binding.start.text.toString()
            val end = binding.end.text.toString()
            if(start.isEmpty() || end.isEmpty()) {
                Toast.makeText(context, "Input fields must not be empty", Toast.LENGTH_SHORT).show()
            }else{
                if (start.toInt() > end.toInt()) {
                    Toast.makeText(context, "Start number cannot be greater than End number", Toast.LENGTH_SHORT).show()
                }else{
                    val action = homeDirections.homeToPrime(start.toInt(), end.toInt())
                    NavHostFragment.findNavController(this).navigate(action)
                    binding.start.text.clear()
                    binding.end.text.clear()
                }
            }
        }
    }

}