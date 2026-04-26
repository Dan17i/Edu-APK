package com.sena.quimbayaeval.presentation.ui.activities

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.google.android.material.snackbar.Snackbar
import com.sena.quimbayaeval.databinding.ActivityMainBinding
import com.sena.quimbayaeval.presentation.viewmodels.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupUI()
        observeViewModel()
    }

    private fun setupUI() {
        binding.btnLogin.setOnClickListener {
            val email = binding.etEmail.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()

            if (validateInputs(email, password)) {
                viewModel.login(email, password)
            }
        }

        binding.tvRegister.setOnClickListener {
            // TODO: Implementar pantalla de registro
            showMessage("Registro próximamente disponible")
        }
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            viewModel.uiState.collect { state ->
                when {
                    state.isLoading -> {
                        binding.btnLogin.isEnabled = false
                        binding.progressBar.show()
                    }
                    state.error != null -> {
                        binding.btnLogin.isEnabled = true
                        binding.progressBar.hide()
                        showMessage(state.error)
                        clearPassword()
                    }
                    state.isLoggedIn -> {
                        binding.btnLogin.isEnabled = true
                        binding.progressBar.hide()
                        navigateToDashboard()
                    }
                }
            }
        }
    }

    private fun validateInputs(email: String, password: String): Boolean {
        return when {
            email.isEmpty() -> {
                showMessage("Por favor ingrese su email")
                false
            }
            password.isEmpty() -> {
                showMessage("Por favor ingrese su contraseña")
                false
            }
            !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                showMessage("Formato de email inválido")
                false
            }
            password.length < 6 -> {
                showMessage("La contraseña debe tener al menos 6 caracteres")
                false
            }
            else -> true
        }
    }

    private fun showMessage(message: String) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_LONG).show()
    }

    private fun clearPassword() {
        binding.etPassword.text.clear()
    }

    private fun navigateToDashboard() {
        val intent = Intent(this, DashboardActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }
}
