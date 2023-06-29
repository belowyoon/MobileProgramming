package com.example.photo

import android.Manifest
import android.nfc.Tag
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import com.example.photo.databinding.ActivityMainBinding
import com.example.photo.picture.PictureAdapter
import com.example.photo.picture.PictureRepository

class MainActivity : AppCompatActivity() {
    companion object {
        const val TAG = "MainActivity"
    }

    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    private val permissionArray = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
    private val permissionRequest = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ){
        request ->
        request.forEach { (p , isGranted) ->
            Log.d(TAG, "$p: $isGranted")
            if (p== permissionArray[0] && isGranted == true){
//                PictureRepository().getImageList()?.forEach {
//                    Log.d(TAG, "onCreate: $it")
//                }
            }
        }
    }

    lateinit private var pictureAdapter: PictureAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        permissionRequest.launch(permissionArray)

        binding.recyclerView.run {
            pictureAdapter = PictureAdapter(PictureRepository().getImageList()!!)
            adapter = pictureAdapter
        }

        PictureRepository().getImageList()?.forEach {
            Log.d(TAG, "onCreate: $it")
        }
    }
}