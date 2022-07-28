package uz.madgeeks.mimovie.presentation

import android.Manifest
import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.util.Log
import android.view.View
import android.widget.Toast
import com.bumptech.glide.Glide
import uz.madgeeks.mimovie.data.DownloadService
import uz.madgeeks.mimovie.databinding.FragmentImageViewerBinding

class ImageViewerFragment :
    BaseFragment<FragmentImageViewerBinding>(FragmentImageViewerBinding::inflate) {
    private val REQUEST_CODE = 156

    private val receiver = object : BroadcastReceiver() {
        @SuppressLint("SetTextI18n")
        override fun onReceive(context: Context?, intent: Intent) {
            val bundle = intent.extras
            if (bundle != null) {
                val resultCode = bundle.getInt(DownloadService.RESULT)
                if (resultCode == 0) {
                    val progress = bundle.getInt(DownloadService.PROGRESS)
                    Log.d("DDDD", "onReceive: $progress")
                    binding.btnDownload.visibility = View.GONE
                    binding.error.visibility = View.GONE
                    binding.check.visibility = View.GONE
                } else if (resultCode == 1) {
                    val path = bundle.getString(DownloadService.FILEPATH)
                    Toast.makeText(requireContext(),
                        "Download complete. Download URI: $path",
                        Toast.LENGTH_LONG).show()
                    binding.check.visibility = View.VISIBLE
                    binding.btnDownload.visibility = View.GONE
                    binding.error.visibility = View.GONE
                    binding.progressBar.visibility = View.GONE
                } else {
                    Toast.makeText(requireContext(), "Download failed",
                        Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    override fun onViewCreate() {
        val IMAGE_URL = requireArguments().getString("IMAGE_URL",
            "https://external-content.duckduckgo.com/iu/?u=http%3A%2F%2Fwww.trendycovers.com%2Fcovers%2F1324229779.jpg&f=1&nofb=1")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requireActivity().requestPermissions(
                arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_EXTERNAL_STORAGE), REQUEST_CODE)
        }

        Glide.with(this)
            .load(IMAGE_URL)
            .into(binding.photoView)

        binding.btnDownload.setOnClickListener {
            val intent = Intent(requireContext(), DownloadService::class.java)
            intent.putExtra(DownloadService.FILENAME, "music")
            intent.putExtra(DownloadService.URL, IMAGE_URL)
            binding.progressBar.visibility = View.VISIBLE
            binding.btnDownload.visibility = View.GONE
            binding.error.visibility = View.GONE
            requireActivity().startService(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        requireActivity().registerReceiver(receiver, IntentFilter(
            DownloadService.NOTIFICATION))
    }

    override fun onPause() {
        super.onPause()
        requireActivity().unregisterReceiver(receiver)
    }
}