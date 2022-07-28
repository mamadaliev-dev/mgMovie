package uz.madgeeks.mimovie.presentation

import android.os.CountDownTimer
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint
import uz.madgeeks.mimovie.R
import uz.madgeeks.mimovie.databinding.FragmentSplashBinding

@AndroidEntryPoint
class SplashFragment : BaseFragment<FragmentSplashBinding>(FragmentSplashBinding::inflate) {
    override fun onViewCreate() {
        object : CountDownTimer(2000, 100) {
            override fun onFinish() {
                navController.navigate(R.id.action_splashFragment_to_navigation_home)
            }

            override fun onTick(value: Long) {

            }
        }.start()
    }
}