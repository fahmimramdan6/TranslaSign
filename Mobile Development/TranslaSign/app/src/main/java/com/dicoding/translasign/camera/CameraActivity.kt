package com.dicoding.translasign.camera

import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.ViewTreeObserver
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.Preview
import androidx.camera.core.resolutionselector.AspectRatioStrategy
import androidx.camera.core.resolutionselector.ResolutionSelector
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.dicoding.translasign.R
import com.dicoding.translasign.data.detection.ImageClassifierHelper
import com.dicoding.translasign.databinding.ActivityCameraBinding
import org.tensorflow.lite.task.gms.vision.classifier.Classifications
import java.text.NumberFormat
import java.util.concurrent.Executors

class CameraActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCameraBinding
    private lateinit var imageClassifierHelper: ImageClassifierHelper
    private var cameraSelector: CameraSelector = CameraSelector.DEFAULT_BACK_CAMERA
    private lateinit var imageAnalyzer: ImageAnalysis

    private var state: Boolean = false
    private val stringBuilder = StringBuilder()
    private val handler = Handler(Looper.getMainLooper())

    private val appendTask = object : Runnable {
        override fun run() {
            val resultText = binding.tvLetter.text.toString()
            if (resultText.isNotEmpty()) {
                stringBuilder.append(resultText[1])
                binding.tvResult.text = stringBuilder.toString()
            }
            handler.postDelayed(this, 2000)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCameraBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.switchCamera.setOnClickListener {
            cameraSelector =
                if (cameraSelector == CameraSelector.DEFAULT_BACK_CAMERA) CameraSelector.DEFAULT_FRONT_CAMERA
                else CameraSelector.DEFAULT_BACK_CAMERA
            startCamera()
        }

        binding.btnToggle.setOnClickListener {
            toggleTranslation()
        }

        binding.btnClear.setOnClickListener {
            stringBuilder.clear()
            binding.tvResult.text = ""
        }

        binding.viewFinder.viewTreeObserver.addOnGlobalLayoutListener(object :
            ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                binding.viewFinder.viewTreeObserver.removeOnGlobalLayoutListener(this)
                startCamera()
            }
        })
    }

    public override fun onResume() {
        super.onResume()
        hideSystemUI()
    }

    public override fun onPause() {
        super.onPause()
        handler.removeCallbacks(appendTask)
    }

    private fun startCamera() {
        imageClassifierHelper = ImageClassifierHelper(
            context = this,
            classifierListener = object : ImageClassifierHelper.ClassifierListener {
                override fun onError(error: String) {
                    runOnUiThread {
                        Toast.makeText(this@CameraActivity, error, Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onResults(results: List<Classifications>?, inferenceTime: Long) {
                    runOnUiThread {
                        results?.let { it ->
                            println(Thread.currentThread())
                            if (it.isNotEmpty() && it[0].categories.isNotEmpty()) {
                                println(it)
                                val sortedCategories =
                                    it[0].categories.sortedByDescending { it?.score }
                                val displayResult = sortedCategories.joinToString("\n") {
                                    "\"${numberToLetter(it.label.toInt())}\" Accuracy: " + NumberFormat.getPercentInstance()
                                        .format(it.score).trim()
                                }
                                binding.tvLetter.text = displayResult
                                binding.tvInferenceTime.text =
                                    getString(R.string.inference_result, inferenceTime)
                            } else {
                                binding.tvLetter.text = ""
                                binding.tvInferenceTime.text = ""
                            }
                        }
                    }
                }
            }
        )

        val cameraProviderFuture = ProcessCameraProvider.getInstance(this)

        cameraProviderFuture.addListener({
            val resolutionSelector = ResolutionSelector.Builder()
                .setAspectRatioStrategy(AspectRatioStrategy.RATIO_16_9_FALLBACK_AUTO_STRATEGY)
                .build()
            imageAnalyzer = ImageAnalysis.Builder()
                .setResolutionSelector(resolutionSelector)
                .setTargetRotation(binding.viewFinder.display.rotation)
                .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                .setOutputImageFormat(ImageAnalysis.OUTPUT_IMAGE_FORMAT_RGBA_8888)
                .build()
            imageAnalyzer.setAnalyzer(Executors.newSingleThreadExecutor()) { image ->
                imageClassifierHelper.classifyImage(image)
            }

            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()
            val preview = Preview.Builder()
                .build()
                .also {
                    it.setSurfaceProvider(binding.viewFinder.surfaceProvider)
                }

            try {
                cameraProvider.unbindAll()
                cameraProvider.bindToLifecycle(
                    this,
                    cameraSelector,
                    preview,
                    imageAnalyzer
                )

            } catch (exc: Exception) {
                Toast.makeText(
                    this@CameraActivity,
                    "Gagal memunculkan kamera.",
                    Toast.LENGTH_SHORT
                ).show()
                Log.e(TAG, "startCamera: ${exc.message}")
            }
        }, ContextCompat.getMainExecutor(this))
    }

    private fun toggleTranslation() {
        if (state) {
            binding.btnToggle.setCompoundDrawablesWithIntrinsicBounds(
                AppCompatResources.getDrawable(
                    this,
                    R.drawable.play_arrow_24px
                ), null, null, null
            )
            binding.btnToggle.text = getString(R.string.start)
            state = false
            handler.removeCallbacks(appendTask)
        } else {
            binding.btnToggle.setCompoundDrawablesWithIntrinsicBounds(
                AppCompatResources.getDrawable(
                    this,
                    R.drawable.pause_24px
                ), null, null, null
            )
            binding.btnToggle.text = getString(R.string.pause)
            state = true
            handler.post(appendTask)
        }
    }

    private fun hideSystemUI() {
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        supportActionBar?.hide()
    }

    private fun numberToLetter(number: Int): Char {
        require(number in 0..25) { "Number must be in the range 0 to 25" }
        return ('A' + number)
    }

    companion object {
        private const val TAG = "CameraActivity"
    }
}