package com.dmytryk.kotlincounter

import android.view.WindowManager.LayoutParams
import android.app.Dialog
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import android.widget.EditText
import kotlinx.android.synthetic.main.fragment_counter_list.*


/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [CounterListFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [CounterListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CounterListFragment : Fragment(){

    // TODO: Rename and change types of parameters
    private var mParam1: String? = null
    private var mParam2: String? = null

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


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (arguments != null) {
            mParam1 = arguments.getString(ARG_PARAM1)
            mParam2 = arguments.getString(ARG_PARAM2)
        }

    }


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment


        return inflater!!.inflate(R.layout.fragment_counter_list, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = CounterListAdapter(context, countersList)
        listViewCounters.adapter = adapter
        adapter.setOnLayoutClickListener(object: CounterListAdapter.OnLayoutClickListener{

            override fun onLayoutClick(currentCounter: CounterData) {

                Log.d("CALLBACK", "onLayoutClick in CounterListFragment")
                val fragmentSwitchTo = CounterActivityFragment()

                val fragmentTransaction = fragmentManager.beginTransaction()
                fragmentTransaction.replace(R.id.frameContainer, fragmentSwitchTo).commit()

            }

        })

        fab.setOnClickListener {
            val dialog = Dialog(context)
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

    }




    companion object {
        // TODO: Rename parameter arguments, choose names that match
        // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
        private val ARG_PARAM1 = "param1"
        private val ARG_PARAM2 = "param2"

        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment CounterListFragment.
         */
        // TODO: Rename and change types and number of parameters
        fun newInstance(param1: String, param2: String): CounterListFragment {
            val fragment = CounterListFragment()
            val args = Bundle()
            args.putString(ARG_PARAM1, param1)
            args.putString(ARG_PARAM2, param2)
            fragment.arguments = args
            return fragment
        }
    }
}// Required empty public constructor
