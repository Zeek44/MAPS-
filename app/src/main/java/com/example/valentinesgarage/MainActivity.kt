package com.example.valentinesgarage

import android.os.Bundle
import android.view.MenuItem
import android.widget.TextView
import androidx.activity.addCallback
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController
import com.google.android.material.navigation.NavigationView
import com.example.valentinesgarage.viewmodel.AuthViewModel

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var authViewModel: AuthViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        authViewModel = ViewModelProvider(this)[AuthViewModel::class.java]

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        drawerLayout = findViewById(R.id.drawerLayout)
        val navigationView = findViewById<NavigationView>(R.id.navigationView)
        navigationView.setNavigationItemSelectedListener(this)

        val toggle = ActionBarDrawerToggle(
            this, drawerLayout, toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        val navHost = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        setupActionBarWithNavController(navHost.navController)

        authViewModel.currentUser.observe(this) { user ->
            val headerView = navigationView.getHeaderView(0)
            val tvName = headerView.findViewById<TextView>(R.id.tvDrawerName)
            val tvRole = headerView.findViewById<TextView>(R.id.tvDrawerRole)
            if (user != null) {
                tvName.text = user.fullName.ifEmpty { user.username }
                tvRole.text = user.role.replaceFirstChar { it.uppercase() }
            } else {
                tvName.text = "Guest"
                tvRole.text = ""
            }
        }

        onBackPressedDispatcher.addCallback(this) {
            val navHost = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
            val navController = navHost.navController
            if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                drawerLayout.closeDrawer(GravityCompat.START)
            } else if (!navController.navigateUp()) {
                finish()
            }
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_home -> {
                val navHost = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
                navHost.navController.navigateUp()
            }
            R.id.nav_logout -> {
                authViewModel.logout()
                val navHost = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
                navHost.navController.navigate(R.id.loginFragment)
            }
        }
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navHost = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        return navHost.navController.navigateUp() || super.onSupportNavigateUp()
    }
}