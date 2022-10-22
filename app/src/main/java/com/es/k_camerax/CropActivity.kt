package com.es.k_camerax

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import com.es.k_camerax.databinding.ActivityCropBinding
import kotlinx.coroutines.launch

class CropActivity : AppCompatActivity() {


    private lateinit var binding: ActivityCropBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCropBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bitmap = assetToBitmap(intent.extras?.getString("uri")!!)

        binding.documentScanner.setOnLoadListener { loading ->
            binding.progressBar.isVisible = loading
        }
        binding.documentScanner.setImage(bitmap)
        binding.btnImageCrop.setOnClickListener {
            lifecycleScope.launch {
                binding.progressBar.isVisible = true
                val image = binding.documentScanner.getCroppedImage()
                binding.progressBar.isVisible = false
                binding.resultImage.isVisible = true
                binding.resultImage.setImageBitmap(image)
            }
        }


    }

    private fun assetToBitmap(file: String): Bitmap =
        contentResolver.openInputStream(Uri.parse(file)).run {
            BitmapFactory.decodeStream(this)
        }
}