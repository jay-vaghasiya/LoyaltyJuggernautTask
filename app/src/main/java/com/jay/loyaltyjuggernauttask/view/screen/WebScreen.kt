package com.jay.loyaltyjuggernauttask.view.screen

import android.annotation.SuppressLint
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun WebScreen(url: String, modifier: Modifier = Modifier) {
    Column(modifier = Modifier.fillMaxSize()) {
        AndroidView(
            factory = { ctx ->
                WebView(ctx).apply {
                    settings.apply {
                        javaScriptEnabled = true
                        loadWithOverviewMode = true
                        useWideViewPort = true
                        setSupportZoom(true)
                    }
                    webViewClient = WebViewClient()
                }
            },
            update = { webView -> webView.loadUrl(url) },
            modifier = Modifier.weight(1f)
        )
    }

}