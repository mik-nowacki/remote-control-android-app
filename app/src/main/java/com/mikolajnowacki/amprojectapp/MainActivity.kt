package com.mikolajnowacki.amprojectapp

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import android.os.Bundle
import android.text.TextUtils.replace
import android.view.MenuItem
import com.google.android.material.navigation.NavigationBarView
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.mikolajnowacki.amprojectapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), NavigationBarView.OnItemSelectedListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var bundle: Bundle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        bundle = Bundle()
        bundle.putString("userID", intent.getSerializableExtra("userID") as String)

        binding.bottomNav.setOnItemSelectedListener(this)

    }

    private fun onControllerClicked(): Boolean {
        supportFragmentManager.commit {
            val controllerFragment = ControllerFragment()
            controllerFragment.arguments = bundle
            replace(R.id.frame_content, controllerFragment)
        }
        return true

    }


    private fun onMotorsClicked(): Boolean {
        supportFragmentManager.commit {
            val motorsFragment = MotorsFragment()
            motorsFragment.arguments = bundle
            replace(R.id.frame_content, motorsFragment)
        }
        return true

    }

    override fun onNavigationItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.nav_controller -> onControllerClicked()

        R.id.nav_motors -> onMotorsClicked()

        else -> false
    }
}
