package com.dmytryk.kotlincounter

import android.support.v4.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.activity_counter.*

import kotlinx.android.synthetic.main.fragment_counter.*
class CounterActivityFragment : Fragment() {

    private val counter:CounterData = CounterData("Counter", 0)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_counter, container, false)
    }

    fun setScore(){
        tvCounterScore.text = counter.score.toString()
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tvCounterName.text = counter.counterName
        tvCounterScore.text = counter.score.toString()
        fab.setImageDrawable(resources.getDrawable(R.drawable.ic_view_list_white_24dp))

        buttonPlus.setOnClickListener {
            val scoreTextViewContent:String = tvCounterScore.text.toString()
            counter.score = scoreTextViewContent.toInt()
            counter.score++
//            tvCounterScore.text = counter.score.toString()
            setScore()
        }

        buttonMinus.setOnClickListener{
            counter.score = tvCounterScore.text.toString().toInt()
            counter.score -= 1
//            tvCounterScore.text = counter.score.toString()
            setScore()
        }

        buttonReset.setOnClickListener {
            counter.score = 0
            setScore()

        }
    }
}
