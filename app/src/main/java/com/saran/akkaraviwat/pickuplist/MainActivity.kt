package com.saran.akkaraviwat.pickuplist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.saran.akkaraviwat.pickuplist.pickuplist.PickupListFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, PickupListFragment.newInstance())
                .commitNow()
        }
    }
}
