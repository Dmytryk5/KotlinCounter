package com.dmytryk.kotlincounter

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CounterData(val counterName: String, var score:Int):Parcelable

