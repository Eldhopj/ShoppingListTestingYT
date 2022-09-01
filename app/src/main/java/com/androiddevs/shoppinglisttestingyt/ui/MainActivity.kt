package com.androiddevs.shoppinglisttestingyt.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.androiddevs.shoppinglisttestingyt.R
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

/**
 * Room DB testing is an Instrumentation test since we have to use SQLite DB in our device
 *
 * */
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var fragmentFactory: ShoppingFragmentFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportFragmentManager.fragmentFactory = fragmentFactory
        setContentView(R.layout.activity_main)
    }
}
