package com.dmytryk.kotlincounter

import android.content.Context
import android.support.v4.app.Fragment
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
//import kotlinx.android.synthetic.main.activity_counter.*
import kotlinx.android.synthetic.main.fragment_counter.*

class CounterActivityFragment : Fragment() {

    private val counter:CounterData = CounterData("Counter", 0)
    private lateinit var callback: OnCounterScoreChangeListener

    override fun onAttach(context: Context?) {
        super.onAttach(context)

        if (context is OnCounterScoreChangeListener){
            callback = context
        }
        else throw ClassCastException(context.toString() +
                " must implement OnCounterScoreChangeListener")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_counter, container, false)
    }

    private fun setScore(){

        tvCounterScore.text = counter.score.toString()
        callback.onCounterScoreChange(counter.counterName, counter.score)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tvCounterName.text = counter.counterName
        tvCounterScore.text = counter.score.toString()
//        fab.setImageDrawable(resources.getDrawable(R.drawable.ic_view_list_white_24dp))
//
        fab.setOnClickListener {
            Log.d("FAB CLICKED", "FAB in CounterActivityFragment clicked")

            val fragmentSwitchTo = CounterListFragment()


            val fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.frameContainer, fragmentSwitchTo).commit()
        }

        buttonPlus.setOnClickListener {
            val scoreTextViewContent:String = tvCounterScore.text.toString()
            counter.score = scoreTextViewContent.toInt()
            counter.score++
            setScore()
        }

        buttonMinus.setOnClickListener{
            counter.score = tvCounterScore.text.toString().toInt()
            counter.score -= 1
            setScore()
        }

        buttonReset.setOnClickListener {
            counter.score = 0
            setScore()

        }
    }

    interface OnCounterScoreChangeListener{
        fun onCounterScoreChange(name: String, newScore: Int)
    }
}
