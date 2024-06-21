package com.dicoding.translasign.dashboard

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.dicoding.translasign.R
import com.dicoding.translasign.ViewModelFactory
import com.dicoding.translasign.camera.CameraActivity
import com.dicoding.translasign.databinding.FragmentDashboardBinding
import com.dicoding.translasign.videotranslator.VideoActivity

class DashboardFragment : Fragment() {
    private val viewModel by viewModels<DashboardViewModel> {
        ViewModelFactory.getInstance(requireContext())
    }

    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val view = binding.root

        ViewCompat.setOnApplyWindowInsetsListener(view.findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getSession().observe(viewLifecycleOwner) { user ->
            if (user.isLogin) {
                if (user.username.length >= 8) {
                    binding.textView2.text = user.username.take(8)
                } else {
                    binding.textView2.text = user.username
                }
                binding.btnVideoTranslation.setOnClickListener {
                    startActivity(Intent(context, VideoActivity::class.java))
                }
            } else {
                binding.textView2.text = getString(R.string.guest)
                binding.btnVideoTranslation.setOnClickListener {
                    context?.let {
                        AlertDialog.Builder(it).apply {
                            setTitle("Oops..")
                            setMessage("Please login first to use this feature.")
                            setPositiveButton("Continue") { _, _ -> }
                            create()
                            show()
                        }
                    }
                }
            }
        }

        binding.btnCamera.setOnClickListener {
            startActivity(Intent(context, CameraActivity::class.java))
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
