package uz.madgeeks.mimovie.data

import android.app.IntentService
import android.content.Intent
import android.os.Environment
import android.util.Log
import java.io.*
import java.net.URL


//https://www.vogella.com/tutorials/AndroidServices/article.html
//music player//https://www.raywenderlich.com/20123726-android-services-getting-started
//https://www.geeksforgeeks.org/services-in-android-with-example/
//https://www.youtube.com/watch?v=bA7v1Ubjlzw
//https://github.com/pavan5208/android_sample_foreground_service
//https://github.com/jeetdholakia/ServicesAndBackgroundTasks
class DownloadService : IntentService("DownloadService") {
    private var result = -1

    // will be called asynchronously by Android
    @Deprecated("Deprecated in Java")
    override fun onHandleIntent(intent: Intent?) {
        val urlPath = intent!!.getStringExtra(URL)
        val fileName = intent.getStringExtra(FILENAME)
        var filePath = ""
        var count: Int
        try {
            val url = URL(urlPath)
            val connection = url.openConnection()
            connection.connect()
            val lengthOfFile = connection.contentLength
            val input: InputStream = BufferedInputStream(url.openStream(), 8192)
            val directory =
                File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).absolutePath + "/miMovie")
            if (!directory.exists()) {
                directory.mkdir()
            }

            val file = File(directory.absolutePath, "${(100000..999999).shuffled().last()}.jpg")
            filePath = file.absolutePath
            val output: OutputStream = FileOutputStream(file)
            val data = ByteArray(1024)
            var total: Long = 0
            while (input.read(data).also { count = it } != -1) {
                total += count.toLong()
                publishProgress((total * 100 / lengthOfFile).toInt(), 0)
                Log.d(TAG, "Progress: " + (total * 100 / lengthOfFile).toInt())
                output.write(data, 0, count)
            }
            output.flush()
            output.close()
            input.close()
            result = 1
        } catch (e: Exception) {
            Log.e("Error: ", e.message!!)
        }
        publishResults(filePath, result)
    }

    private fun publishResults(outputPath: String, result: Int) {
        val intent = Intent(NOTIFICATION)
        intent.putExtra(FILEPATH, outputPath)
        intent.putExtra(RESULT, result)
        sendBroadcast(intent)
    }

    private fun publishProgress(progress: Int, result: Int) {
        val intent = Intent(NOTIFICATION)
        intent.putExtra(PROGRESS, progress)
        intent.putExtra(RESULT, result)
        sendBroadcast(intent)
    }

    companion object {
        const val URL = "urlpath"
        const val FILENAME = "filename"
        const val FILEPATH = "filepath"
        const val RESULT = "result"
        const val PROGRESS = "progress"
        const val NOTIFICATION = "uz.madgeeks.mimovie"
        private const val TAG = "LOADER"
    }
}