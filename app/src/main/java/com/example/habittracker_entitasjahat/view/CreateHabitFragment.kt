package com.example.habittracker_entitasjahat.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.habittracker_entitasjahat.databinding.FragmentCreateHabitBinding
import com.example.habittracker_entitasjahat.viewmodel.HabitViewModel

class CreateHabitFragment : Fragment() {
    private lateinit var binding: FragmentCreateHabitBinding
    private lateinit var viewModel: HabitViewModel

    private val iconOptions = arrayOf(
        "Water",
        "Fitness",
        "Book",
        "Meditation"
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentCreateHabitBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity()).get(HabitViewModel::class.java)

        val spinnerAdapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item,
            iconOptions
        )

        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerIcon.adapter = spinnerAdapter

        binding.btnBack.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.btnCreateHabit.setOnClickListener {
            val name = binding.txtHabitName.text.toString()
            val description = binding.txtDescription.text.toString()
            val goalText = binding.txtGoal.text.toString()
            val unit = binding.txtUnit.text.toString()
            val iconName = binding.spinnerIcon.selectedItem.toString()

            if(name.isEmpty() || description.isEmpty() || goalText.isEmpty() || unit.isEmpty()){
                Toast.makeText(requireContext(),"Semua field wajib diisi",Toast.LENGTH_SHORT).show()
            }else{
                val goal=goalText.toIntOrNull()
                if(goal == null || goal <= 0){
                    Toast.makeText(requireContext(),"Goal harus berupa angka lebih dari 0",Toast.LENGTH_SHORT).show()
                }else{
                    viewModel.addHabit(
                        name=name,
                        description=description,
                        goal=goal,
                        unit=unit,
                        iconName=iconName
                    )
                    Toast.makeText(requireContext(),"Habit berhasil ditambahkan",Toast.LENGTH_SHORT).show()
                    findNavController().navigateUp()
                }
            }
        }
    }
}