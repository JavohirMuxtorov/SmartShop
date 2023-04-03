package com.example.smartshop.view

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import android.widget.TextView
import com.example.smartshop.R
import com.example.smartshop.activity.AboutDetailActivity

class ELAdapter(var context: Context,var title:MutableList<String>,var subtitle: MutableList<MutableList<String>>): BaseExpandableListAdapter() {
    override fun getGroupCount(): Int {
        return title.size
    }

    override fun getChildrenCount(groupPosition: Int): Int {
        return subtitle[groupPosition].size
    }

    override fun getGroup(groupPosition: Int): Any {
        return title[groupPosition]
    }

    override fun getChild(groupPosition: Int, childPosition: Int): Any {

        return subtitle[groupPosition][childPosition]
    }

    override fun getGroupId(groupPosition: Int): Long {
        return groupPosition.toLong()
    }

    override fun getChildId(groupPosition: Int, childPosition: Int): Long {
        return childPosition.toLong()
    }

    override fun hasStableIds(): Boolean {
        return false
    }

    override fun getGroupView(
        groupPosition: Int,
        isExpanded: Boolean,
        convertView: View?,
        parent: ViewGroup?
    ): View {
        var convertView = convertView
        if (convertView == null){
            val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            convertView = inflater.inflate(R.layout.title_layout,null)
        }
        val mmTitle = convertView!!.findViewById<TextView>(R.id.lv_title)
        mmTitle.text = getGroup(groupPosition).toString()
        return convertView
    }

    override fun getChildView(
        groupPosition: Int,
        childPosition: Int,
        isLastChild: Boolean,
        convertView: View?,
        parent: ViewGroup?
    ): View {
        var convertView = convertView
        if (convertView == null){
            val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            convertView = inflater.inflate(R.layout.subtitle_layout,null)
        }
        val mSubTitle = convertView!!.findViewById<TextView>(R.id.lv_subtitle)
        mSubTitle.text = getChild(groupPosition, childPosition).toString()
        return convertView

    }

    override fun isChildSelectable(groupPosition: Int, childPosition: Int): Boolean {
        return true
    }
}