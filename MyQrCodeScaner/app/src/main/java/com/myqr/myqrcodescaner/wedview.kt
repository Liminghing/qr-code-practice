package com.myqr.myqrcodescaner

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient

class wedview : AppCompatActivity() {
    private lateinit var webView: WebView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wedview)
        webView = findViewById(R.id.webView)

        // 웹 페이지 로드를 WebView 내부에서 처리하도록 설정
        webView.webViewClient = WebViewClient()

        // 로드할 URL 설정
        val receivedData = intent.getStringExtra("str")

        webView.loadUrl(receivedData!!)
    }
}