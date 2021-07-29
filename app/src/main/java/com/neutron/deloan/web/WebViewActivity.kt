package com.neutron.deloan.web

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.webkit.WebResourceRequest
import android.webkit.WebSettings
import android.webkit.WebView
import android.widget.LinearLayout
import com.just.agentweb.AgentWeb
import com.just.agentweb.WebViewClient
import com.neutron.deloan.R
import com.neutron.deloan.base.IBaseActivity
import com.neutron.deloan.utils.Constants
import com.neutron.deloan.utils.Slog
import kotlinx.android.synthetic.main.activity_web_view.*

class WebViewActivity : IBaseActivity() {
    var mAgentWeb: AgentWeb? = null

    override fun getLayoutId(): Int {
        return R.layout.activity_web_view
    }

    override fun initData() {
        Slog.d("initData $intent")
        val intent = intent
        if (intent != null) {
            val uri = intent.getStringExtra(Constants.Intent_URI)
            uri?.let {
                loadURI(it)
            }

        }
    }

    override fun initView() {
//        var titleStr = ""
//        intent?.getStringExtra(Constants.ACTIVITY_ACTION_TITLE)?.let {
//            titleStr = it
//        }
//        initToolbar(titleStr,true)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
        }
        return super.onOptionsItemSelected(item)
    }


    override fun onPause() {
        mAgentWeb!!.webLifeCycle.onPause()
        super.onPause()
    }

    override fun onResume() {
        mAgentWeb!!.webLifeCycle.onResume()
        super.onResume()
    }


    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        return if (mAgentWeb?.handleKeyEvent(keyCode, event) == true) {
            true
        } else super.onKeyDown(keyCode, event)
    }

    override fun onDestroy() {

        mAgentWeb?.webLifeCycle?.onDestroy()
        super.onDestroy()

    }

    private fun loadURI(url: String) {

        val mWebViewClient: WebViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(
                view: WebView?,
                request: WebResourceRequest?): Boolean {
//拦截网页加载位置
                request?.let {
                    Slog.d("拦截网页加载位置 it.url  ${it.url}")
                    val uriStr = it.url.toString()
                    Slog.d("拦截网页加载位置  $uriStr")
                }

                return false


            }
        }


        mAgentWeb = AgentWeb.with(this)
            .setAgentWebParent(
                fl_main,
                LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
            )

            .useDefaultIndicator()
            .setWebViewClient(mWebViewClient)
            .createAgentWeb()
            .ready()
            .go(url)

        Slog.d("访问  $url")
        //优先使用网络
        val webViewSetting = mAgentWeb?.webCreator?.webView?.settings
        webViewSetting?.cacheMode = WebSettings.LOAD_DEFAULT
        //将图片调整到适合webview的大小
        webViewSetting?.useWideViewPort = true
        //隐藏缩放按钮
        webViewSetting?.displayZoomControls = false

        //支持内容重新布局
        webViewSetting?.layoutAlgorithm = WebSettings.LayoutAlgorithm.SINGLE_COLUMN
        //支持自动加载图片
        webViewSetting?.loadsImagesAutomatically = true
        //当webview调用requestFocus时为webview设置节点
        webViewSetting?.setNeedInitialFocus(true)
        //自适应屏幕
        webViewSetting?.useWideViewPort = true

        webViewSetting?.javaScriptEnabled=true

        webViewSetting?.loadWithOverviewMode = true
        //开启DOM storage API功能（HTML5 提供的一种标准的接口，主要将键值对存储在本地，在页面加载完毕后可以通过 javascript 来操作这些数据。）
        webViewSetting?.domStorageEnabled = true
        //支持缩放
        webViewSetting?.builtInZoomControls = true
        webViewSetting?.setSupportZoom(true)
        //允许webview对文件的操作
        webViewSetting?.allowFileAccess = true
        webViewSetting?.allowFileAccessFromFileURLs = true

//        2021年3月22日 安全检测 修改为false
        webViewSetting?.allowUniversalAccessFromFileURLs = true

        //
        mAgentWeb?.webCreator?.webView
            ?.setOnKeyListener(View.OnKeyListener { _, keyCode, event ->
                if (event.action == KeyEvent.ACTION_DOWN) {
                    if (keyCode == KeyEvent.KEYCODE_BACK && mAgentWeb!!.webCreator
                            .webView.canGoBack()
                    ) { // 表示按返回键时的操作
                        mAgentWeb?.webCreator!!.webView.goBack() // 后退
                        // webview.goForward();//前进
                        return@OnKeyListener true // 已处理
                    } else if (keyCode == KeyEvent.KEYCODE_BACK) {
//                        moveTaskToBack(true);
                        finish()
                    }
                }
                false
            })


    }


}