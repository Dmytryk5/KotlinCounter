package com.dmytryk.kotlincounter

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.dmytryk.kotlincounter.CounterActivityFragment.OnCounterScoreChangeListener

import kotlinx.android.synthetic.main.activity_counter.*

class CounterActivity : AppCompatActivity(), OnCounterScoreChangeListener{

    private lateinit var counterData: CounterData

    override fun onCounterScoreChange(name: String, newScore: Int) {
        counterData = CounterData(name, newScore)
    }

    override fun onCounterListCalled() {

        val fragmentSwitchTo = CounterListFragment.newInstance(counterData)


        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frameContainer, fragmentSwitchTo).commit()

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_counter)
        setSupportActionBar(toolbar)

        if (savedInstanceState == null) {//fixes rotation problem
            val counter = CounterData("Counter", 0)
            val fragment = CounterActivityFragment.newInstance(counter)

            val fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.frameContainer, fragment).commit()

        }



    }

//
//    override fun onCreateOptionsMenu(menu: Menu): Boolean {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        menuInflater.inflate(R.menu.menu_counter, menu)
//        return true
//    }

//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        return when (item.itemId) {
//            R.id.action_settings -> true
//            else -> super.onOptionsItemSelected(item)
//        }
//    }
}
