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

    private lateinit var counter:CounterData
    private lateinit var callback: OnCounterScoreChangeListener
    private val counterKey: String = "counter_key"
//
//    private var counterScore : Int = counter.score
//    private var counterName : String = counter.counterName


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

        if (savedInstanceState == null) {
            setCounter(CounterData("Standard counter", 2))
        } else {
            setCounter(savedInstanceState.getParcelable(counterKey))
//            counter =
//            setScore()
        }
        return inflater.inflate(R.layout.fragment_counter, container, false)
    }

    fun setCounter(newCounter:CounterData){
        this.counter = newCounter
//        this.counterName = counter.counterName
//        this.counterScore = counter.score
    }

    private fun setScore(){

        tvCounterName.text = counter.counterName
        tvCounterScore.text = counter.score.toString()
        callback.onCounterScoreChange(counter.counterName, counter.score)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)




//
//        tvCounterName.text = counter.counterName
//        tvCounterScore.text = counter.score.toString()
        setScore()
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

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState?.putParcelable(counterKey, counter)

    }

//    override fun onActivityCreated(savedInstanceState: Bundle?) {
//        super.onActivityCreated(savedInstanceState)
//        if (savedInstanceState != null) {
//            setCounter(savedInstanceState.getParcelable(counterKey))
////            counter =
//            setScore()
//        }

//        counter = savedInstanceState?.getParcelable(counterKey)

//    }

    interface OnCounterScoreChangeListener{
        fun onCounterScoreChange(name: String, newScore: Int)
    }
}
