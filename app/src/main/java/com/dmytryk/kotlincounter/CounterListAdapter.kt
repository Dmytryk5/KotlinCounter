package com.dmytryk.kotlincounter

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.support.design.widget.Snackbar
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView


class CounterListAdapter(context: Context, val counterList:MutableList<CounterData> ):
        BaseAdapter(){

    private val inflater:LayoutInflater =
            context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    private lateinit var callback : OnLayoutClickListener

    fun setOnLayoutClickListener(listener : OnLayoutClickListener){
        callback = listener
    }


    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var view = convertView
        if (view == null){
            view = inflater.inflate(R.layout.counter_list_item, parent, false);
        }

        val counter: CounterData = getCounter(position)

        view?.findViewById<TextView>(R.id.listCounterNameTv)?.text = counter.counterName
        view?.findViewById<TextView>(R.id.listScoreTv)?.text = counter.score.toString()
        view?.findViewById<ImageButton>(R.id.buttonDelete)?.setOnClickListener {

            counterList.removeAt(position)
            Snackbar.make(view!!, R.string.counter_deleted, Snackbar.LENGTH_LONG)
                    .setAction(R.string.undo, View.OnClickListener {
                        Log.d("SNACK", "undo clicked / position = $position")
                        counterList.add(position, counter)
                        this.notifyDataSetChanged()
                    }).show()

            notifyDataSetChanged()

        }

        view?.findViewById<ConstraintLayout>(R.id.counterListItemLayout)?.setOnClickListener {
            Log.d("LAYOUT", "layout $position clicked")

            //check if callback not null without using {?}
            if (::callback.isInitialized) {
                callback.onLayoutClick(counterList[position])
            }

        }

        return view as View

    }

    fun getCounter(position:Int):CounterData{
        return getItem(position) as CounterData
    }

    override fun getItem(position: Int): Any {
        return counterList.get(position)
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return counterList.size
    }


    interface OnLayoutClickListener{
        fun onLayoutClick(currentCounter:CounterData)
    }
}