package com.dicoding.translasign.register

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.dicoding.translasign.R
import com.dicoding.translasign.ViewModelFactory
import com.dicoding.translasign.databinding.ActivityRegisterBinding
import kotlinx.coroutines.launch

class RegisterActivity : AppCompatActivity() {
    private val viewModel by viewModels<RegisterViewModel> {
        ViewModelFactory.getInstance(this)
    }

    private lateinit var binding: ActivityRegisterBinding

    private var errorMessage: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        showLoading(false)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        viewModel.isLoading.observe(this) {
            showLoading(it)
        }

        binding.tvSignin.setOnClickListener {
            finish()
        }

        binding.btnSignup.setOnClickListener {
            with(binding) {
                val name = nameEditText.text.toString()
                val username = usernameEditText.text.toString()
                val email = emailEditText.text.toString()
                val password = passwordEditText.text.toString()

                lifecycleScope.launch {
                    viewModel.register(name, username, email, password)
                }
            }
        }

        viewModel.message.observe(this@RegisterActivity) {
            errorMessage = it
        }

        viewModel.success.observe(this@RegisterActivity) {
            val email = binding.emailEditText.text.toString()
            if (it) {
                AlertDialog.Builder(this@RegisterActivity).apply {
                    setTitle("Yeah!")
                    setMessage(getString(R.string.register_success, email))
                    setPositiveButton("Continue") { _, _ ->
                        finish()
                    }
                    create()
                    show()
                }
            } else {
                AlertDialog.Builder(this@RegisterActivity).apply {
                    setTitle("Oops..")
                    setMessage(getString(R.string.register_fail, errorMessage))
                    setPositiveButton("Continue") { _, _ -> }
                    create()
                    show()
                }
            }
        }
    }

    private fun showLoading(state: Boolean) {
        binding.progressBar.visibility = if (state) View.VISIBLE else View.GONE
    }
}