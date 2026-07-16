package com.example.habittracker_entitasjahat.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.habittracker_entitasjahat.databinding.FragmentEditHabitBinding
import com.example.habittracker_entitasjahat.viewmodel.HabitViewModel
import kotlinx.coroutines.launch

class EditHabitFragment : Fragment() {
    private lateinit var binding: FragmentEditHabitBinding
    private lateinit var viewModel: HabitViewModel

    private val iconOptions = arrayOf("Water", "Fitness", "Book", "Meditation")

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentEditHabitBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity()).get(HabitViewModel::class.java)

        val spinnerAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, iconOptions)
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerIcon.adapter = spinnerAdapter

        binding.btnBack.setOnClickListener { findNavController().navigateUp() }

        val habitId = arguments?.getInt("habitId") ?: return

        lifecycleScope.launch {
            val habitToEdit = viewModel.getHabitById(habitId)
            if (habitToEdit != null) {
                binding.habit = habitToEdit

                val spinnerPosition = spinnerAdapter.getPosition(habitToEdit.iconName)
                if (spinnerPosition >= 0) {
                    binding.spinnerIcon.setSelection(spinnerPosition)
                }
            }
        }

        binding.btnUpdateHabit.setOnClickListener {
            val currentHabit = binding.habit

            if (currentHabit != null) {
                val goalText = binding.txtGoal.text.toString()
                val goal = goalText.toIntOrNull()

                if (goal == null || goal <= 0) {
                    Toast.makeText(requireContext(), "Goal harus berupa angka lebih dari 0", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }

                currentHabit.goal = goal
                currentHabit.iconName = binding.spinnerIcon.selectedItem.toString()

                viewModel.updateHabitData(currentHabit)

                Toast.makeText(requireContext(), "Habit berhasil diperbarui", Toast.LENGTH_SHORT).show()
                findNavController().navigateUp()
            }
        }
    }
}