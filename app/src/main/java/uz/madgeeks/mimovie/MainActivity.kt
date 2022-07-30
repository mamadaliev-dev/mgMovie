package uz.madgeeks.mimovie

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint
import uz.madgeeks.mimovie.databinding.ActivityMainBinding


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        navView.visibility = View.GONE

        val navController = findNavController(R.id.nav_host_fragment_activity_main)

        appBarConfiguration = AppBarConfiguration(setOf(
            R.id.navigation_home,
            R.id.navigation_tvshows,
            R.id.navigation_search,
            R.id.navigation_genre,
            R.id.navigation_actors))
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            when (destination.id) {
                R.id.navigation_home,
                R.id.navigation_tvshows,
                R.id.navigation_search,
                R.id.navigation_genre,
                R.id.navigation_actors,
                -> {
                    binding.navView.visibility = View.VISIBLE
                    binding.imageView.visibility = View.VISIBLE
                    binding.divider.visibility = View.VISIBLE
                    supportActionBar?.show()
                    actionBar?.show()
                }
                R.id.navigation_splash -> {
                    binding.navView.visibility = View.GONE
                    binding.imageView.visibility = View.GONE
                    binding.divider.visibility = View.GONE
                    supportActionBar?.hide()
                    actionBar?.hide()
                }
                else -> {
                    binding.navView.visibility = View.GONE
                    binding.imageView.visibility = View.GONE
                    binding.divider.visibility = View.GONE
                    supportActionBar?.show()
                    actionBar?.show()
                }
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id: Int = item.itemId
        if (id == android.R.id.home) {
            super.onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}