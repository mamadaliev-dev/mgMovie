package uz.madgeeks.mimovie.presentation

import android.os.Bundle
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.youtube.player.YouTubeBaseActivity
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.google.android.youtube.player.YouTubePlayerView
import uz.madgeeks.mimovie.R


class YoutubeActivity : YouTubeBaseActivity(), YouTubePlayer.OnInitializedListener {
    private var YOUTUBE_VIDEO_ID: String = "2U76x2fD_tE"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        YOUTUBE_VIDEO_ID =
            intent.extras!!.getString("YOUTUBE_VIDEO_ID", "2U76x2fD_tE")

        val layout = layoutInflater.inflate(R.layout.activity_youtube, null) as FrameLayout
        setContentView(layout)

        val playerView = YouTubePlayerView(this)
        playerView.layoutParams = ConstraintLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT
        )
        layout.addView(playerView)

        playerView.initialize(getString(R.string.GOOGLE_API_KEY), this)
    }

    override fun onInitializationSuccess(
        provider: YouTubePlayer.Provider?, youTubePlayer: YouTubePlayer?,
        wasRestored: Boolean,
    ) {
        youTubePlayer?.loadVideo(YOUTUBE_VIDEO_ID)
        youTubePlayer?.play()
    }

    override fun onInitializationFailure(
        provider: YouTubePlayer.Provider?,
        youTubeInitializationResult: YouTubeInitializationResult?,
    ) {
        val REQUEST_CODE = 0

        if (youTubeInitializationResult?.isUserRecoverableError == true) {
            youTubeInitializationResult.getErrorDialog(this, REQUEST_CODE).show()
        } else {
            val errorMessage =
                "There was an error initializing the YoutubePlayer ($youTubeInitializationResult)"
            Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show()
        }
    }
}