package com.dmytryk.kotlincounter

import android.view.WindowManager.LayoutParams
import android.app.Dialog
import android.app.Fragment
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import android.widget.EditText
import kotlinx.android.synthetic.main.fragment_counter_list.*


class CounterListFragment : Fragment(){

    private val countersList: MutableList<CounterData> =
            mutableListOf(
                    CounterData("Default Counter", 0),
                    CounterData("Additional Counter", 12),
                    CounterData("Additional Counter", 12),
                    CounterData("Additional Counter", 12),
                    CounterData("Additional Counter", 12),
                    CounterData("Additional Counter", 12),
                    CounterData("Additional Counter", 12),
                    CounterData("Additional Counter", 12),
                    CounterData("Additional Counter", 12),
                    CounterData("Additional Counter", 12),
                    CounterData("Additional Counter", 12),
                    CounterData("Днів без алкоголю", 1))


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment


        return inflater!!.inflate(R.layout.fragment_counter_list, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = CounterListAdapter(this.activity, countersList)
        listViewCounters.adapter = adapter
        adapter.setOnLayoutClickListener(object: CounterListAdapter.OnLayoutClickListener{

            override fun onLayoutClick(currentCounter: CounterData) {

                Log.d("CALLBACK", "onLayoutClick in CounterListFragment")


                val fragmentSwitchTo = CounterActivityFragment.newInstance(currentCounter)
//
//                val arguments = Bundle()
//                arguments.putString("counterName", currentCounter.counterName)
//                arguments.putInt("counterScore", currentCounter.score)
//
//                fragmentSwitchTo.arguments = arguments

                val fragmentTransaction = fragmentManager.beginTransaction()
                fragmentTransaction.replace(R.id.frameContainer, fragmentSwitchTo).commit()

            }
        })

        fab.setOnClickListener {
            val dialog = Dialog(activity)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setContentView(R.layout.add_counter_dialog_layout)
            val buttonAdd = dialog.findViewById<Button>(R.id.buttonAdd)
            val buttonCancel = dialog.findViewById<Button>(R.id.buttonCancel)
            val editTextNewCounterName = dialog.findViewById<EditText>(R.id.editTextNewCounterName)

            buttonAdd.setOnClickListener({
                var newName: String = editTextNewCounterName.text.toString()
                if (newName == ""){
                    newName = "Counter"
                }
                adapter.counterList.add(CounterData(newName, 0))
                adapter.notifyDataSetChanged()
                dialog.dismiss()
            })

            buttonCancel.setOnClickListener {
                dialog.dismiss()
            }
            dialog.window.setSoftInputMode(LayoutParams.SOFT_INPUT_STATE_VISIBLE)
            dialog.show()
        }


        if (arguments != null) {
            val counterName = arguments.getString(UPDATED_COUNTER_NAME)
            val counterScore = arguments.getInt(UPDATED_COUNTER_SCORE)

            val counter = CounterData(counterName, counterScore)
            var update = false
            countersList.forEach {
                if (it.counterName == counterName) {
                    it.score = counterScore
                    update = true
                    return
                }
            }

            if (!update){
                countersList.add(counter)
            }
        }

    }




    companion object {
        // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
        private const val UPDATED_COUNTER_NAME = "updatedCounterName"
        private const val UPDATED_COUNTER_SCORE = "updatedCounterScore"

        fun newInstance(currentCounter: CounterData): CounterListFragment {
            val fragment = CounterListFragment()
            val args = Bundle()
            args.putString(UPDATED_COUNTER_NAME, currentCounter.counterName)
            args.putInt(UPDATED_COUNTER_SCORE, currentCounter.score)
            fragment.arguments = args
            return fragment
        }
    }
}// Required empty public constructor
