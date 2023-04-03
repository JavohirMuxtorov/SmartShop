package com.example.smartshop.view

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.marginBottom
import com.example.smartshop.R

class CustomSpinnerAdapter(context: Context, items: Array<String>) : ArrayAdapter<String>(context, R.layout.spinner_item_layout, items) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = super.getView(position, convertView, parent) as TextView
        view.setTextColor(Color.RED)
        view.visibility = View.GONE
        view.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16f)
        return view
    }

    override fun isEnabled(position: Int): Boolean {
        return position !=0
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = super.getDropDownView(position, convertView, parent) as TextView
        if (view.isPressed) {
            view.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16f)
            view.setTypeface(null, Typeface.BOLD)
            view.setPadding(8,8,8,8)
        } else {
            view.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16f)
            view.setTypeface(null, Typeface.NORMAL)
            view.setPadding(16,16,16,16)
        }
        return view
    }

}