package com.rickyhu.hdriver

import android.os.Bundle
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class WebViewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_view)

        val webView = findViewById<WebView>(R.id.web_view)
        webView.settings.javaScriptEnabled = true
        webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(
                view: WebView?,
                request: WebResourceRequest?
            ): Boolean {
                view?.loadUrl(request?.url.toString())
                return true
            }
        }

        val url = intent.getStringExtra("url")
        if (url != null)
            webView.loadUrl(url)
        else
            showOpenWebViewFailToast()
    }

    private fun showOpenWebViewFailToast() {
        val toast = Toast.makeText(applicationContext, "無法開啟頁面", Toast.LENGTH_SHORT)
        toast.show()
    }
}
