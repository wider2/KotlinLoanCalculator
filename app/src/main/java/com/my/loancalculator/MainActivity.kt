package com.my.loancalculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.activity.viewModels
import com.my.loancalculator.fragments.CalcFragment
import com.my.loancalculator.sync.SyncViewModel
import com.my.loancalculator.sync.SyncViewModelFactory
import com.my.loancalculator.utils.addFragmentSafely

class MainActivity : AppCompatActivity() {

    private val viewModel: SyncViewModel by viewModels {
        SyncViewModelFactory()
    }
    val textViewOutput by lazy { findViewById<TextView>(R.id.textViewOutput) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) { //fragment initialize
            addFragmentSafely(
                fragment = CalcFragment(),
                tag = CalcFragment::class.java.simpleName,
                containerViewId = R.id.fragmentContainer,
                allowStateLoss = true
            )

            setupObservers()
            viewModel.setContext(baseContext)
            viewModel.getSyncProfiles()
        }
    }

    // listen for the rxjava observers of users profiles
    private fun setupObservers() {
        viewModel.showSync.observe(this, EventObserver {
            showsSync(it)
        })
    }

    private fun showsSync(str: String) {
        textViewOutput?.text = str //just to show status of sync
    }

}