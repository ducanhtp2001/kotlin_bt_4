package com.example.democontentprovider.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.democontentprovider.Model.Work
import com.example.democontentprovider.R

class WorkAdapter(val context: Context, val layoutId: Int, val list: ArrayList<Work>): BaseAdapter() {
    override fun getCount(): Int {
        return list.size
    }

    override fun getItem(position: Int): Any {
        return list.get(position)
    }

    override fun getItemId(position: Int): Long {
        return list.get(position).id.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var view: View?
        var viewHolder: MyViewHolder
        if(convertView == null) {
            view = LayoutInflater.from(context).inflate(layoutId, null)
            viewHolder = MyViewHolder(view)
            view.setTag(viewHolder)
        } else {
            view = convertView
            viewHolder = view.tag as MyViewHolder
        }

        viewHolder.txtId.text = list.get(position).id.toString()
        viewHolder.txtContent.text = list.get(position).content.toString()
        viewHolder.txtDate.text = list.get(position).date.toString()

        return view!!
    }

    inner class MyViewHolder(view: View) {
        var txtId: TextView = view.findViewById(R.id.txtId)
        var txtContent: TextView = view.findViewById(R.id.txtContent)
        var txtDate: TextView = view.findViewById(R.id.txtDate)
    }
}