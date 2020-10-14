package com.nasejevs.htmlfetcher

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity


class AddFetch : AppCompatActivity() {
    private lateinit var webView: WebView;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_fetch)

        this.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        this.actionBar?.setDisplayHomeAsUpEnabled(true)

        this.webView = findViewById<WebView>(R.id.webView);
        this.webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(
                view: WebView?,
                request: WebResourceRequest?
            ): Boolean {
                return false
            }
        }
        webView.settings.builtInZoomControls = true;
        webView.settings.javaScriptEnabled = true;
        webView.loadUrl("https://www.ss.com")
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }

        return super.onOptionsItemSelected(item)
    }

    fun confirmUrl(view: View?) {
        val resultIntent = Intent()

        Log.d(Constants.LOG_MAIN_ACTIVITY, "URL is: " + webView.url)

        resultIntent.putExtra(Constants.NEW_FETCH_URL, webView.url)
        setResult(RESULT_OK, resultIntent)
        finish()
    }
}