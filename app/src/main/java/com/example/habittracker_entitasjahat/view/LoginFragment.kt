package com.example.habittracker_entitasjahat.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.habittracker_entitasjahat.R
import com.example.habittracker_entitasjahat.databinding.FragmentLoginBinding
import com.example.habittracker_entitasjahat.util.SessionManager
import com.example.habittracker_entitasjahat.viewmodel.LoginViewModel

class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding
    private lateinit var viewModel: LoginViewModel
    private lateinit var sessionManager: SessionManager

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

        sessionManager = SessionManager(requireContext())

        // Bonus: kalau sesi sebelumnya masih tersimpan, langsung masuk Dashboard
        if (sessionManager.isLoggedIn()) {
            findNavController().navigate(R.id.action_loginFragment_to_dashboardFragment)
            return
        }

        viewModel = ViewModelProvider(this)[LoginViewModel::class.java]

        binding.txtError.visibility = View.GONE

        binding.btnLogin.setOnClickListener {
            val username = binding.txtUsername.text.toString().trim()
            val password = binding.txtPassword.text.toString().trim()

            if (username.isEmpty() || password.isEmpty()) {
                binding.txtError.text = "Username dan password wajib diisi"
                binding.txtError.visibility = View.VISIBLE
            } else {
                viewModel.login(username, password)
            }
        }

        viewModel.loginResult.observe(viewLifecycleOwner, Observer { isSuccess ->
            if (isSuccess == null) return@Observer

            if (isSuccess) {
                binding.txtError.visibility = View.GONE
                sessionManager.saveSession(username = binding.txtUsername.text.toString().trim())
                findNavController().navigate(R.id.action_loginFragment_to_dashboardFragment)
            } else {
                binding.txtError.text = "Username atau password salah"
                binding.txtError.visibility = View.VISIBLE
            }

            viewModel.resetLoginResult()
        })
    }
}