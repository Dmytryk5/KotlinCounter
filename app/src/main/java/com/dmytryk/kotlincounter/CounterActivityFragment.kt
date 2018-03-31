package com.dmytryk.kotlincounter

import android.app.Fragment
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_counter.*

class CounterActivityFragment : Fragment() {

    private lateinit var counter:CounterData
    private lateinit var callback: OnCounterScoreChangeListener
    private val counterKey: String = "counter_key"

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        if (activity is OnCounterScoreChangeListener){
            callback = activity as OnCounterScoreChangeListener
        }
        else throw ClassCastException(activity.toString() +
                " must implement OnCounterScoreChangeListener")

        if (savedInstanceState == null) {
            if (arguments != null) {
                setCounter(CounterData(arguments.getString(COUNTER_NAME_KEY),
                        arguments.getInt(COUNTER_SCORE_KEY)))
            }
        } else {
            setCounter(savedInstanceState.getParcelable(counterKey))
//            counter =
//            setScore()
        }
        return inflater.inflate(R.layout.fragment_counter, container, false)
    }


    private fun setCounter(newCounter:CounterData){
        this.counter = newCounter
    }

    private fun setScore(){

        tvCounterName.text = counter.counterName
        tvCounterScore.text = counter.score.toString()
        callback.onCounterScoreChange(counter.counterName, counter.score)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        setScore()
        fab.setOnClickListener {
            Log.d("FAB CLICKED", "FAB in CounterActivityFragment clicked")
            this.callback.onCounterListCalled()
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


    interface OnCounterScoreChangeListener{
        fun onCounterScoreChange(name: String, newScore: Int)
        fun onCounterListCalled()
    }


    companion object {
        private const val COUNTER_NAME_KEY = "counterName"
        private const val COUNTER_SCORE_KEY = "counterScore"

        fun newInstance(counter : CounterData): CounterActivityFragment {
            val fragment = CounterActivityFragment()
            val args = Bundle()
            args.putString(COUNTER_NAME_KEY, counter.counterName)
            args.putInt(COUNTER_SCORE_KEY, counter.score)
            fragment.arguments = args
            return fragment
        }
    }
}
