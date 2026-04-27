package com.example.habittracker_entitasjahat.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.habittracker_entitasjahat.R
import com.example.habittracker_entitasjahat.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.txtError.visibility = View.GONE

        binding.btnLogin.setOnClickListener {
            val username = binding.txtUsername.text.toString()
            val password = binding.txtPassword.text.toString()

            if (username == "student" && password == "123") {
                binding.txtError.visibility = View.GONE
                findNavController().navigate(R.id.action_loginFragment_to_dashboardFragment)
            } else {
                binding.txtError.text = "Username atau password salah"
                binding.txtError.visibility = View.VISIBLE
            }
        }
    }
}