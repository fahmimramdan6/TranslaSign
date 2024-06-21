package com.dicoding.translasign.videotranslator

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import com.dicoding.translasign.R
import com.dicoding.translasign.ViewModelFactory
import com.dicoding.translasign.databinding.ActivityVideoBinding
import java.io.File

class VideoActivity : AppCompatActivity() {
    private val viewModel by viewModels<VideoViewModel> {
        ViewModelFactory.getInstance(this)
    }

    private val REQUEST_VIDEO_PICK = 1
    private val REQUEST_PERMISSION = 2

    private lateinit var binding: ActivityVideoBinding
    private lateinit var player: ExoPlayer
    private var selectedVideoUri: Uri? = null
    private var errorMessage: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityVideoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        showLoading(false)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        player = ExoPlayer.Builder(this).build().also { exoPlayer ->
            exoPlayer.prepare()
        }
        binding.playerView.player = player

        binding.btnAdd.setOnClickListener {
            checkPermission()
        }
        binding.btnClear.setOnClickListener {
            clearPlayer()
        }
        binding.btnTranslate.setOnClickListener {
            translateVideo()
        }

        viewModel.isLoading.observe(this) {
            showLoading(it)
        }

        viewModel.result.observe(this) {
            binding.textView11.text = it
        }

        viewModel.message.observe(this) {
            errorMessage = it
        }

        viewModel.success.observe(this) {
            if (it == false) {
                Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun checkPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
            != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), REQUEST_PERMISSION
            )
        } else {
            openGalleryForVideo()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray,
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            REQUEST_PERMISSION -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openGalleryForVideo()
                } else {
                    // Permission denied
                    Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun openGalleryForVideo() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Video.Media.EXTERNAL_CONTENT_URI)
        intent.type = "video/*"
        startActivityForResult(intent, REQUEST_VIDEO_PICK)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_VIDEO_PICK) {
            data?.data?.let { videoUri ->
                selectedVideoUri = videoUri
                playVideo(videoUri)
            }
        }
    }

    private fun playVideo(videoUri: Uri) {
        val videoItem = MediaItem.fromUri(videoUri)
        player.setMediaItem(videoItem)
        player.prepare()
        binding.playerView.player = player
    }

    private fun clearPlayer() {
        player.clearMediaItems()
        selectedVideoUri = null
    }

    private fun translateVideo() {
        selectedVideoUri?.let { uri ->
            val videoFile = File(getRealPathFromUri(uri))
            viewModel.uploadVideo(videoFile)
        } ?: run {
            Toast.makeText(this, "No video selected", Toast.LENGTH_SHORT).show()
        }
    }

    private fun getRealPathFromUri(uri: Uri): String {
        var path = ""
        val cursor = contentResolver.query(uri, null, null, null, null)
        if (cursor != null) {
            cursor.moveToFirst()
            val index = cursor.getColumnIndex(MediaStore.Video.Media.DATA)
            path = cursor.getString(index)
            cursor.close()
        }
        return path
    }

    private fun showLoading(state: Boolean) {
        binding.progressBar.visibility = if (state) View.VISIBLE else View.GONE
    }
}
