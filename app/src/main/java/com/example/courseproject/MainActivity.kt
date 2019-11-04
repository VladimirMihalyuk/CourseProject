package com.example.courseproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.courseproject.database.AppDatabase
import com.example.courseproject.database.Costs
import com.example.courseproject.databinding.ActivityMainBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var drawerLayout: DrawerLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)

        @Suppress("UNUSED_VARIABLE")

        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        val navController = this.findNavController(R.id.nav_host_fragment)
        drawerLayout = binding.drawerLayout


//        navController.addOnDestinationChangedListener { nc: NavController, nd: NavDestination, args: Bundle? ->
//            if (nd.id.equals(R.id.loginFragment) || nd.id.equals(R.id.logUpFragment)) {
//                Log.d("WTF", "LOCK")
//                drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
//                getSupportActionBar()?.setDisplayShowHomeEnabled(true)
//
//            } else {
//                Log.d("WTF", "UNLOCK")
//                drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
//                getSupportActionBar()?.setDisplayShowHomeEnabled(true)
//
//            }
//
//        }
        NavigationUI.setupActionBarWithNavController(this, navController, drawerLayout)
        NavigationUI.setupWithNavController(binding.navView, navController)









//        var database = AppDatabase.getInstance(this.application).databaseDao
//
//        var job = GlobalScope.launch {
//            database.insertAll(Costs("", "", 1F,Calendar.getInstance().getTime(), 1))
//        }
//
//        Thread.sleep(200)

    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = this.findNavController(R.id.nav_host_fragment)
        return NavigationUI.navigateUp(navController, drawerLayout)
    }
}
