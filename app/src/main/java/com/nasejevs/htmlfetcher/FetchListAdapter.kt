package com.nasejevs.htmlfetcher

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView

class FetchListAdapter(var context: Context?, var items: ArrayList<FetchItem>) : BaseAdapter() {

    fun updateItems(items: ArrayList<FetchItem>) {
        this.items = items
    }

    override fun getCount(): Int {
        return items.size
    }

    override fun getItem(position: Int): Any {
        return items[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {
        var view = convertView

        if (view == null) {
            view = LayoutInflater.from(context).
            inflate(R.layout.fetch_list_view, parent, false);

            // get current item to be displayed
            val currentItem = getItem(position) as FetchItem

            // get the TextView for item name and item description
            val url = view.findViewById(R.id.url) as TextView
            val header = view.findViewById(R.id.header) as TextView
            val removeButton = view.findViewById(R.id.remove) as ImageView

            //sets the text for item name and item description from the current item object
            url.text = currentItem.url
            header.text = currentItem.header
            removeButton.tag = position
        }

        // returns the view for the current row
        return view
    }

}