package com.example.habittracker_entitasjahat.view

import android.content.res.ColorStateList
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.habittracker_entitasjahat.R
import com.example.habittracker_entitasjahat.databinding.HabitListItemBinding
import com.example.habittracker_entitasjahat.model.Habit

class HabitListAdapter(
    private val habitList: ArrayList<Habit>,
    private val onPlusClick: (Int) -> Unit,
    private val onMinusClick: (Int) -> Unit
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

    override fun getItemCount(): Int = habitList.size

    override fun onBindViewHolder(holder: HabitViewHolder, position: Int) {
        val habit = habitList[position]
        val binding = holder.binding

        binding.txtHabitName.text = habit.name
        binding.txtDescription.text = habit.description
        binding.txtProgress.text = "${habit.progress} / ${habit.goal} ${habit.unit}"

        binding.progressBar.max = habit.goal
        binding.progressBar.progress = habit.progress

        when (habit.iconName) {
            "Water" -> binding.imgIcon.setImageResource(R.drawable.baseline_water_drop_24)
            "Fitness" -> binding.imgIcon.setImageResource(R.drawable.baseline_fitness_center_24)
            "Book" -> binding.imgIcon.setImageResource(R.drawable.baseline_menu_book_24)
            "Meditation" -> binding.imgIcon.setImageResource(R.drawable.baseline_self_improvement_24)
            else -> binding.imgIcon.setImageResource(R.drawable.ic_launcher_foreground)
        }

        if (habit.isCompleted()) {
            binding.txtStatus.text = "Completed"
            binding.txtStatus.setBackgroundResource(R.drawable.bg_status_completed)
            binding.txtStatus.setTextColor(Color.WHITE)

            binding.progressBar.progressTintList =
                ColorStateList.valueOf(Color.parseColor("#5FA965"))
            binding.progressBar.progressBackgroundTintList =
                ColorStateList.valueOf(Color.parseColor("#DDEFD9"))

            binding.viewAccent.visibility = View.VISIBLE

            // kalau mau PERSIS kayak screenshot: tombol completed dibikin disable semua
            binding.btnPlus.isEnabled = false
            binding.btnMinus.isEnabled = false

            binding.btnPlus.setBackgroundResource(R.drawable.bg_circle_gray)
            binding.btnPlus.imageTintList =
                ColorStateList.valueOf(Color.parseColor("#BDBDBD"))

            binding.btnMinus.setBackgroundResource(R.drawable.bg_circle_gray)
            binding.btnMinus.imageTintList =
                ColorStateList.valueOf(Color.parseColor("#BDBDBD"))
        } else {
            binding.txtStatus.text = "In Progress"
            binding.txtStatus.setBackgroundResource(R.drawable.bg_status_in_progress)
            binding.txtStatus.setTextColor(Color.parseColor("#444444"))

            binding.progressBar.progressTintList =
                ColorStateList.valueOf(Color.parseColor("#6A00F4"))
            binding.progressBar.progressBackgroundTintList =
                ColorStateList.valueOf(Color.parseColor("#E8DBFF"))

            binding.viewAccent.visibility = View.GONE

            binding.btnPlus.isEnabled = true
            binding.btnPlus.setBackgroundResource(R.drawable.bg_circle_purple)
            binding.btnPlus.imageTintList =
                ColorStateList.valueOf(Color.WHITE)

            binding.btnMinus.isEnabled = habit.progress > 0
            binding.btnMinus.setBackgroundResource(R.drawable.bg_circle_gray)
            binding.btnMinus.imageTintList =
                ColorStateList.valueOf(Color.parseColor("#9E9E9E"))
        }

        binding.btnPlus.setOnClickListener {
            val itemPosition = holder.bindingAdapterPosition
            if (itemPosition != RecyclerView.NO_POSITION) {
                onPlusClick(itemPosition)
            }
        }

        binding.btnMinus.setOnClickListener {
            val itemPosition = holder.bindingAdapterPosition
            if (itemPosition != RecyclerView.NO_POSITION) {
                onMinusClick(itemPosition)
            }
        }
    }

    fun updateHabitList(newHabitList: ArrayList<Habit>) {
        habitList.clear()
        habitList.addAll(newHabitList)
        notifyDataSetChanged()
    }
}