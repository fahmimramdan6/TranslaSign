package com.dicoding.translasign.login

import android.content.Intent
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
import com.dicoding.translasign.dashboard.MainActivity
import com.dicoding.translasign.databinding.ActivityLoginBinding
import com.dicoding.translasign.register.RegisterActivity
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {
    private val viewModel by viewModels<LoginViewModel> {
        ViewModelFactory.getInstance(this)
    }

    private lateinit var binding: ActivityLoginBinding

    private var errorMessage: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        showLoading(false)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.tvSignup.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }

        binding.btnSignin.setOnClickListener {
            val username = binding.usernameEditText.text.toString()
            val password = binding.passwordEditText.text.toString()

            lifecycleScope.launch {
                viewModel.login(username, password)
            }
        }

        viewModel.errorMessage.observe(this) {
            errorMessage = it
        }

        viewModel.success.observe(this) {
            if (it) {
                viewModel.userModel.observe(this) { user ->
                    viewModel.saveSession(user)
                }
                AlertDialog.Builder(this@LoginActivity).apply {
                    setTitle("Yeah!")
                    setMessage(
                        getString(
                            R.string.login_success,
                            viewModel.userModel.value?.username
                        )
                    )
                    setPositiveButton("Continue") { _, _ ->
                        val intent = Intent(context, MainActivity::class.java)
                        intent.flags =
                            Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                        startActivity(intent)
                        finish()
                    }
                    create()
                    show()
                }
            } else {
                AlertDialog.Builder(this@LoginActivity).apply {
                    setTitle("Oops..")
                    setMessage(getString(R.string.login_fail, errorMessage))
                    setPositiveButton("Continue") { _, _ -> }
                    create()
                    show()
                }
            }
        }
        viewModel.isLoading.observe(this) {
            showLoading(it)
        }
    }

    private fun showLoading(state: Boolean) {
        binding.progressBar.visibility = if (state) View.VISIBLE else View.GONE
    }
}