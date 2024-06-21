package com.dicoding.translasign.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.dicoding.translasign.R
import com.dicoding.translasign.ViewModelFactory
import com.dicoding.translasign.databinding.FragmentProfileBinding
import com.dicoding.translasign.login.LoginActivity


class ProfileFragment : Fragment() {
    private val viewModel by viewModels<ProfileViewModel> {
        ViewModelFactory.getInstance(requireContext())
    }

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val view = binding.root

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getSession().observe(viewLifecycleOwner) { user ->
            if (user.isLogin) {
                binding.textView8.text = user.username
            } else {
                binding.textView8.text = getString(R.string.guest)
            }

            if (user.isLogin) {
                binding.button.text = getString(R.string.logout)
            } else {
                binding.button.text = getString(R.string.sign_in)
            }
        }

        viewModel.getSession().observe(viewLifecycleOwner) { user ->
            binding.button.setOnClickListener {
                if (user.isLogin) {
                    viewModel.logout()
                    Toast.makeText(
                        requireContext(),
                        getString(R.string.logout_successful),
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    startActivity(Intent(requireContext(), LoginActivity::class.java))
                }
            }
        }
    }
}
