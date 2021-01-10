package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.navigation.fragment.NavHostFragment
import com.google.android.material.navigation.NavigationView
import com.example.myapplication.fragment.account.AccountFragment
import com.example.myapplication.fragment.home.HomeFragment
import com.example.myapplication.fragment.profile.ProfileFragment
import com.example.myapplication.fragment.settings.SettingsFragment
import com.example.myapplication.fragment.support.SupportFragment
import com.example.myapplication.listener.OnFragmentInteractionListener
import com.example.myapplication.fragment.transfer.TransferFragment

import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener,
    OnFragmentInteractionListener {
    var logoutButton: Button? = null;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(app_bar)

        val toggle = ActionBarDrawerToggle(this, drawer_layout, app_bar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)
        nav_view.setCheckedItem(R.id.nav_home);

        supportFragmentManager.beginTransaction().add(
            R.id.contentFragment,
            HomeFragment()
        ).commit()

    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu to use in the action bar
        val inflater = menuInflater
        inflater.inflate(R.menu.obe_toolbar_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here.
        var id = item.itemId
        if(id == R.id.logout){
            startActivity(Intent(this@MainActivity, LoginActivity::class.java))
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_home -> {
                supportFragmentManager.beginTransaction().replace(R.id.contentFragment,
                    HomeFragment()
                )
                    .commit()
            }
            R.id.nav_transfer -> {
                supportFragmentManager.beginTransaction().replace(R.id.contentFragment,
                    TransferFragment()
                )
                    .commit()
            }
            R.id.nav_account -> {
                supportFragmentManager.beginTransaction().replace(R.id.contentFragment,
                    AccountFragment()
                )
                    .commit()
            }
            R.id.nav_profile -> {
                supportFragmentManager.beginTransaction().replace(R.id.contentFragment,
                    ProfileFragment()
                )
                    .commit()
            }
            R.id.nav_notifications -> {
                val host = NavHostFragment.create(R.navigation.notification_nav_graph)
                supportFragmentManager.beginTransaction().replace(R.id.contentFragment,
                    host
                ).setPrimaryNavigationFragment(host)
                    .commit()
            }
            R.id.nav_support -> {
                supportFragmentManager.beginTransaction().replace(R.id.contentFragment,
                    SupportFragment()
                )
                    .commit()
            }
            R.id.nav_settings -> {
                supportFragmentManager.beginTransaction().replace(R.id.contentFragment,
                    SettingsFragment()
                )
                    .commit()
            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

}
