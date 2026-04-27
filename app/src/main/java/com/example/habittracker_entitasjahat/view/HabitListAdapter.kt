package com.example.habittracker_entitasjahat.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.habittracker_entitasjahat.R
import com.example.habittracker_entitasjahat.databinding.HabitListItemBinding
import com.example.habittracker_entitasjahat.model.Habit

class HabitListAdapter(
    val habitList: ArrayList<Habit>,
    val onPlusClick: (Int) -> Unit,
    val onMinusClick: (Int) -> Unit
) : RecyclerView.Adapter<HabitListAdapter.HabitViewHolder>() {

    class HabitViewHolder(var binding: HabitListItemBinding)
        : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HabitViewHolder {
        val binding = HabitListItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return HabitViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return habitList.size
    }

    override fun onBindViewHolder(holder: HabitViewHolder, position: Int) {
        val habit = habitList[position]

        holder.binding.txtHabitName.text = habit.name
        holder.binding.txtDescription.text = habit.description
        holder.binding.txtProgress.text = "${habit.progress} / ${habit.goal} ${habit.unit}"

        holder.binding.progressBar.max = habit.goal
        holder.binding.progressBar.progress = habit.progress

        if (habit.progress >= habit.goal) {
            holder.binding.txtStatus.text = "Completed"
        } else {
            holder.binding.txtStatus.text = "In Progress"
        }

        when (habit.iconName) {
            "Water" -> holder.binding.imgIcon.setImageResource(R.drawable.baseline_water_drop_24)
            "Fitness" -> holder.binding.imgIcon.setImageResource(R.drawable.baseline_fitness_center_24)
            "Book" -> holder.binding.imgIcon.setImageResource(R.drawable.baseline_menu_book_24)
            "Meditation" -> holder.binding.imgIcon.setImageResource(R.drawable.baseline_self_improvement_24)
            else -> holder.binding.imgIcon.setImageResource(R.drawable.ic_launcher_foreground)
        }

        holder.binding.btnPlus.setOnClickListener {
            onPlusClick(position)
        }

        holder.binding.btnMinus.setOnClickListener {
            onMinusClick(position)
        }
    }

    fun updateHabitList(newHabitList: ArrayList<Habit>) {
        habitList.clear()
        habitList.addAll(newHabitList)
        notifyDataSetChanged()
    }
}