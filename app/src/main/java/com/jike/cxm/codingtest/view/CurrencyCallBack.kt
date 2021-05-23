package com.jike.cxm.codingtest.view

import android.view.View

interface CurrencyCallBack {
    fun onCurrencyClick(view: View, position: Int, isLongClick: Boolean)
}
