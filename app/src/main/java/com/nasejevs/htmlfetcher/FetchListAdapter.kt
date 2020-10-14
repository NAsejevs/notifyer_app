package com.nasejevs.htmlfetcher

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
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
            val textViewItemName = view.findViewById(R.id.text_view_item_name) as TextView
            val textViewItemDescription = view.findViewById(R.id.text_view_item_description) as TextView

            //sets the text for item name and item description from the current item object
            textViewItemName.text = currentItem.title
            textViewItemDescription.text = currentItem.description
        }

        // returns the view for the current row
        return view
    }

}