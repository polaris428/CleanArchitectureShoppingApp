package com.android.cleanarchitectureshoppingapp.extensions

import android.content.Context
import android.widget.Toast

internal  fun Context.toast(messge:String){
    Toast.makeText(this,messge,Toast.LENGTH_SHORT).show()
}