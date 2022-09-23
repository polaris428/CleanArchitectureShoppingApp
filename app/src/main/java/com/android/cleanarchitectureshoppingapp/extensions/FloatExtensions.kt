package com.android.cleanarchitectureshoppingapp.extensions

import android.content.res.Resources
import retrofit2.Response

internal fun Float.fromDpToPx():Int{
    return (this * Resources.getSystem().displayMetrics.density).toInt()
}