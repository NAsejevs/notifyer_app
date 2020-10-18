package com.nasejevs.htmlfetcher

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.SharedPreferences.Editor
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.jsoup.Jsoup
import org.jsoup.nodes.Document

class MainActivity : AppCompatActivity() {
    private var doc: Document? = null
    var items = ArrayList<FetchItem>()
    private lateinit var fetchListAdapter: FetchListAdapter
    private lateinit var preferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(findViewById(R.id.my_toolbar))

        fetchListAdapter = FetchListAdapter(this, items)
        findViewById<ListView>(R.id.fetchList).adapter = fetchListAdapter

        loadPreferences()
        fetchDocument("")
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            Constants.REQUEST_NEW_FETCH -> {
                if (resultCode == RESULT_OK) {
                    val fetchUrl = data?.getStringExtra(Constants.NEW_FETCH_URL)
                    if (fetchUrl != null) {
                        items.add(FetchItem(fetchUrl, "Notification header"))
                        fetchListAdapter.updateItems(items)
                        fetchListAdapter.notifyDataSetChanged()
                        savePreferences()
                    }
                }
            }
        }
    }

    private fun fetchDocument(targetUrl: String) {
        var url = targetUrl

        if (url == "") {
            url = "https://www.ss.com/lv/transport/cars/bmw/"
        }

        GlobalScope.launch {
            try {
                doc = Jsoup.connect(url).get()
                runOnUiThread(Runnable {
                    onDocumentFetched()
                })
            } catch (e: Error) {
                Log.e(Constants.LOG_MAIN_ACTIVITY, "Fetch error: $e")
            }
        }
    }

    private fun onDocumentFetched() {
        val document = doc
        if (document != null) {

            val rows = document.select("[id^=tr_]")

            rows.forEach {
                val header = it.select("[id^=dm_]")
            }
        }
    }

    private fun loadPreferences() {
        preferences = getPreferences(Context.MODE_PRIVATE);
        val jsonText: String? = preferences.getString("fetchURLs", null)

        if (jsonText != null) {
            val text = Gson().fromJson(
                jsonText,
                Array<FetchItem>::class.java
            )

            items = text.toCollection(ArrayList<FetchItem>())

            fetchListAdapter.updateItems(items)
            fetchListAdapter.notifyDataSetChanged()
        }
    }

    private fun savePreferences() {
        val text = Gson().toJson(items);

        preferences = getPreferences(Context.MODE_PRIVATE);
        val editor: Editor = preferences.edit()
        editor.putString("fetchURLs", text);
        editor.apply()
    }

    fun onClickFetch(view: View) {
        val intent = Intent(this, AddFetch::class.java)
        this.startActivityForResult(intent, Constants.REQUEST_NEW_FETCH)
    }

    fun onClickRemoveRetch(view: View) {
        items.removeAt(view.tag as Int)
        fetchListAdapter.updateItems(items)
        fetchListAdapter.notifyDataSetChanged()

        savePreferences()
    }
}