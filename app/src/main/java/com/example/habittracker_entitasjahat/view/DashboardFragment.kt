package com.example.habittracker_entitasjahat.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.habittracker_entitasjahat.R
import com.example.habittracker_entitasjahat.databinding.FragmentDashboardBinding
import com.example.habittracker_entitasjahat.viewmodel.HabitViewModel

class DashboardFragment : Fragment() {
    private lateinit var binding: FragmentDashboardBinding
    private lateinit var viewModel: HabitViewModel
    private lateinit var habitListAdapter: HabitListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentDashboardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity()).get(HabitViewModel::class.java)

        habitListAdapter = HabitListAdapter(arrayListOf(), viewModel)

        binding.recViewHabit.layoutManager = LinearLayoutManager(requireContext())
        binding.recViewHabit.adapter = habitListAdapter

        binding.btnAdd.setOnClickListener {
            findNavController().navigate(R.id.action_dashboardFragment_to_createHabitFragment)
        }

        observeViewModel()
        viewModel.loadHabits()
    }

    private fun observeViewModel() {
        viewModel.habitsLD.observe(viewLifecycleOwner, Observer { habitList ->
            habitListAdapter.updateHabitList(habitList)

            if (habitList.isEmpty()) {
                binding.txtEmpty.visibility = View.VISIBLE
            } else {
                binding.txtEmpty.visibility = View.GONE
            }
        })
    }
}