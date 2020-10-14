package com.nasejevs.htmlfetcher

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.jsoup.Jsoup
import org.jsoup.nodes.Document

class MainActivity : AppCompatActivity() {
    private var doc: Document? = null;
    private val items = ArrayList<FetchItem>();
    private val fetchListAdapter = FetchListAdapter(this, items)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        startService(Intent(this, Fetcher::class.java))
        findViewById<ListView>(R.id.fetchList).adapter = fetchListAdapter

        fetchDocument("")
    }

    private fun fetchDocument(targetUrl: String) {
        var url = targetUrl;

        if (url == "") {
            url = "https://www.ss.com/lv/transport/cars/bmw/"
        }

        GlobalScope.launch {
            try {
                doc = Jsoup.connect(url).get()
                runOnUiThread(Runnable() {
                    onDocumentFetched();
                })
            } catch (e: Error) {
                Log.e(Constants.LOG_MAIN_ACTIVITY, "Fetch error: $e")
            }
        }
    }

    private fun onDocumentFetched() {
        Log.e(Constants.LOG_MAIN_ACTIVITY, "Document fetched!")

        val document = doc;
        if (document != null) {
            
            val rows = document.select("[id^=tr_]")

            Log.e(Constants.LOG_MAIN_ACTIVITY, "Document contains: $document")

            items.clear()
            rows.forEach {
                val header = it.select("[id^=dm_]")
                items.add(FetchItem(header.text(), "Cool description bro..."))
            }

            fetchListAdapter.updateItems(items)
            fetchListAdapter.notifyDataSetChanged()
        }
    }

    public fun onClickFetch(view: View) {
        val intent = Intent(this, AddFetch::class.java)
        this.startActivityForResult(intent, Constants.REQUEST_NEW_FETCH)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            Constants.REQUEST_NEW_FETCH -> {
                if (resultCode == RESULT_OK) {
                    val fetchUrl = data?.getStringExtra(Constants.NEW_FETCH_URL)
                    Log.d(Constants.LOG_MAIN_ACTIVITY, "2 URL is: $fetchUrl")
                    if (fetchUrl != null) {
                        fetchDocument(fetchUrl)
                    }
                }
            }
        }
    }
}