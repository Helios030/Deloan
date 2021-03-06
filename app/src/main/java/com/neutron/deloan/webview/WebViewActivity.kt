//package com.neutron.deloan.webview
//
//import android.Manifest
//import android.net.Uri
//import android.view.View
//import android.webkit.WebChromeClient
//import android.webkit.WebView
//import android.webkit.WebViewClient
//import androidx.annotation.StringDef
//import androidx.appcompat.app.AppCompatActivity
//import com.blankj.utilcode.util.AppUtils
//import java.io.File
//import java.lang.annotation.Retention
//import java.lang.annotation.RetentionPolicy
//
//class WebViewActivity : BaseBindingActivity<WebBinding?>(R.layout.activity_url_web_view) {
//    @StringDef(
//        URL_PROFILE,
//        URL_OCR,
//        URL_BASE_INFO,
//        URL_CONTACT_INFO,
//        URL_WORK_INFO,
//        URL_BANK_INFO,
//        URL_REPAY,
//        URL_PERIOD,
//        URL_RECORD,
//        BuildConfig.PRIVACY_POLICY
//    )
//    @Retention(
//        RetentionPolicy.SOURCE
//    )
//    internal annotation class Url
//
//    private var requestCode = 0
//    private var webUrl: String? = null
//    private var title: String? = null
//    private var productId: String? = null
//    private var borrowStatusResp: LoanStatusEntity? = null
//    private var jsUtils: JsUtils? = null
//    private var filePathCallback: ValueCallback<Array<Uri>>? = null
//
//    class StartParams : BaseViewModelNoModel.StartActivityParams {
//        val bundle: Bundle
//
//        @JvmOverloads
//        constructor(@Url webUrl: String?, title: String?, productId: String? = "") : this(
//            0,
//            webUrl,
//            title,
//            productId
//        ) {
//        }
//
//        constructor(requestCode: Int, @Url webUrl: String?, title: String?, productId: String?) {
//            bundle = Bundle()
//            bundle.putInt("request_code", requestCode)
//            bundle.putString("web_url", webUrl)
//            bundle.putString("title", title)
//            if (productId != null) {
//                bundle.putString("product_id", productId)
//            }
//        }
//
//        constructor(@Url webUrl: String?, title: String?, borrowStatusResp: LoanStatusEntity?) {
//            bundle = Bundle()
//            bundle.putInt("request_code", 0)
//            bundle.putString("web_url", webUrl)
//            bundle.putString("title", title)
//            bundle.putSerializable("borrow_status_resp", borrowStatusResp)
//        }
//    }
//
//    fun initParam() {
//        super.initParam()
//        val bundle: Bundle = getIntent().getBundleExtra("bundle")
//        requestCode = bundle.getInt("request_code", 0)
//        webUrl = bundle.getString("web_url")
//        title = bundle.getString("title")
//        if (bundle.containsKey("product_id")) {
//            productId = bundle.getString("product_id")
//        }
//        if (bundle.containsKey("borrow_status_resp")) {
//            borrowStatusResp = bundle.getSerializable("borrow_status_resp") as LoanStatusEntity
//        }
//        println("aaaaaaaaaaaa==========$webUrl")
//        println("bbbbbbbbbbbbb==========$productId")
//    }
//
//    fun initView() {
//        super.initView()
//        ImmersionBar.setStatusBarView(this, binding.titleBar.viewStatusBar)
//    }
//
//    fun initData() {
//        super.initData()
//        initWebView()
//    }
//
//    @SuppressLint("SetJavaScriptEnabled", "JavascriptInterface")
//    private fun initWebView() {
//        if (!TextUtils.isEmpty(title)) {
//            binding.titleBar.flTitle.setVisibility(View.VISIBLE)
//            binding.titleBar.tvTitle.setText(title)
//            binding.titleBar.ivBack.setOnClickListener { v -> finish() }
//        } else {
//            binding.titleBar.flTitle.setVisibility(View.GONE)
//        }
//        if (!BuildConfig.PRIVACY_POLICY.equals(webUrl)) {
//            val listener: JsUtils.Listener = object : JsUtils.Listener {
//                override fun onStartContact(requestCode: Int) {
//                    PermissionUtils.permission(Manifest.permission.READ_CONTACTS)
//                        .callback { isAllGranted, granted, deniedForever, denied ->
//                            if (!deniedForever.isEmpty()) {
//                                ToastUtils.show(StringUtils.getPermissionTips(Manifest.permission.READ_CONTACTS))
//                                AppUtils.launchAppDetailsSettings()
//                            } else if (!isAllGranted) {
//                                ToastUtils.show(StringUtils.getPermissionTips(Manifest.permission.READ_CONTACTS))
//                            } else {
//                                startActivityForResult(
//                                    Intent(Intent.ACTION_PICK).setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE),
//                                    requestCode
//                                )
//                            }
//                        }.request()
//                }
//
//                override fun onStartLive() {
//                    if (!TextUtils.isEmpty(productId)) {
//                        FaceIdentificationActivity.start(
//                            this@WebViewActivity,
//                            requestCode,
//                            productId
//                        )
//                        /*??????webview*/this@WebViewActivity.finish()
//                    }
//                }
//
//                override fun setResult(resultCode: Int, isFinish: Boolean) {
//                    if (resultCode != JsUtils.Companion.NOTHING) {
//                        setResult(resultCode)
//                    }
//                    if (isFinish) {
//                        this@WebViewActivity.finish()
//                    }
//                }
//            }
//            jsUtils = if (borrowStatusResp != null) {
//                JsUtils(borrowStatusResp, listener)
//            } else {
//                JsUtils(!TextUtils.isEmpty(productId), listener)
//            }
//            binding.mWebView.addJavascriptInterface(jsUtils, "androidUtils")
//        }
//        if (TextUtils.isEmpty(webUrl)) {
//            binding.mWebView.setVisibility(View.GONE)
//            binding.mProgressBar.setVisibility(View.GONE)
//            binding.tvContent.setVisibility(View.GONE)
//            binding.svContent.setVisibility(View.GONE)
//        } else if (webUrl!!.startsWith("http") || webUrl!!.startsWith("HTTP")) {
//            binding.mWebView.setVisibility(View.VISIBLE)
//            binding.mProgressBar.setVisibility(View.VISIBLE)
//            binding.tvContent.setVisibility(View.GONE)
//            binding.svContent.setVisibility(View.GONE)
//            // ???????????????
//            val mSettings: WebSettings = binding.mWebView.getSettings()
//            mSettings.setJavaScriptEnabled(true)
//            mSettings.setDomStorageEnabled(true)
//            mSettings.setDefaultTextEncodingName("utf-8")
//            mSettings.setSupportZoom(false)
//            mSettings.setBuiltInZoomControls(false)
//            mSettings.setUseWideViewPort(true)
//            mSettings.setNeedInitialFocus(false)
//            mSettings.setLoadWithOverviewMode(true)
//            mSettings.setRenderPriority(WebSettings.RenderPriority.HIGH)
//            mSettings.setBlockNetworkImage(true)
//            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
//                mSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.TEXT_AUTOSIZING)
//            } else {
//                mSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN)
//            }
//            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
//                mSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW)
//            }
//            binding.mWebView.setWebViewClient(OnWebViewClient())
//            binding.mWebView.setWebChromeClient(OnWebChromeClient())
//            println("ssssssssssssss========$webUrl")
//            binding.mWebView.loadUrl(webUrl)
//        } else {
//            binding.mWebView.setVisibility(View.GONE)
//            binding.mProgressBar.setVisibility(View.GONE)
//            binding.tvContent.setVisibility(View.VISIBLE)
//            binding.svContent.setVisibility(View.VISIBLE)
//            binding.tvContent.setText(webUrl)
//        }
//    }
//
//    inner class OnWebViewClient : WebViewClient() {
//        override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
//            if (url.startsWith("market://details?id=")) {
//                AppStoreUtils.jumpAppStore(this@WebViewActivity, url, "", 2)
//            } else {
//                if (!url.startsWith("http:") && !url.startsWith("https:")) {
//                    try {
//                        val intent = Intent(Intent.ACTION_VIEW)
//                        intent.setData(Uri.parse(url))
//                        startActivity(intent)
//                    } catch (e: ActivityNotFoundException) {
//                        e.printStackTrace()
//                    }
//                } else if (url.startsWith("https://play.google.com/store")) {
//                    AppStoreUtils.jumpAppStore(this@WebViewActivity, url, 2)
//                } else {
//                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//                        return false
//                    } else {
//                        view.loadUrl(url)
//                    }
//                }
//            }
//            return true
//        }
//
//        override fun shouldOverrideUrlLoading(view: WebView, request: WebResourceRequest): Boolean {
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                if (request.getUrl().toString().startsWith("market://details?id=")) {
//                    AppStoreUtils.jumpAppStore(
//                        this@WebViewActivity,
//                        request.getUrl().toString(),
//                        "",
//                        2
//                    )
//                    finish()
//                } else if (request.getUrl().toString()
//                        .startsWith("https://play.google.com/store")
//                ) {
//                    AppStoreUtils.jumpAppStore(this@WebViewActivity, request.getUrl().toString(), 2)
//                } else {
//                    if (!request.getUrl().toString().startsWith("http:") &&
//                        !request.getUrl().toString().startsWith("https:")
//                    ) {
//                        try {
//                            val intent = Intent(Intent.ACTION_VIEW)
//                            intent.setData(Uri.parse(request.getUrl().toString()))
//                            startActivity(intent)
//                        } catch (e: ActivityNotFoundException) {
//                            e.printStackTrace()
//                        }
//                    } else {
//                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//                            return false
//                        } else {
//                            view.loadUrl(request.getUrl().toString())
//                        }
//                    }
//                    return true
//                }
//            }
//            return super.shouldOverrideUrlLoading(view, request)
//        }
//
//        override fun onPageStarted(view: WebView, url: String, favicon: Bitmap) {
//            super.onPageStarted(view, url, favicon)
//            binding.mWebView.getSettings().setBlockNetworkImage(true)
//        }
//
//        override fun onPageFinished(view: WebView, url: String) {
//            super.onPageFinished(view, url)
//            initBarColor(url)
//            println("url===========$url")
//            binding.mProgressBar.setVisibility(View.GONE)
//            binding.mWebView.getSettings().setBlockNetworkImage(false)
//        }
//
//        override fun onReceivedSslError(view: WebView, handler: SslErrorHandler, error: SslError) {}
//    }
//
//    private fun initBarColor(url: String) {
//        if (URL_OCR != url && URL_RECORD != url && URL_PROFILE != url && !BuildConfig.PRIVACY_POLICY.equals(
//                url
//            )
//        ) {
//            binding.titleBar.viewStatusBar.setBackgroundResource(R.color.color_2EDFA3)
//        } else {
//            binding.titleBar.viewStatusBar.setBackgroundResource(R.color.color_2EDFA3)
//        }
//    }
//
//    /**
//     * ?????????WebChromeClient
//     */
//    inner class OnWebChromeClient : WebChromeClient() {
//        override fun onProgressChanged(view: WebView, newProgress: Int) {
//            super.onProgressChanged(view, newProgress)
//            if (newProgress < 100) {
//                binding.mProgressBar.setVisibility(View.VISIBLE)
//                binding.mProgressBar.setProgress(newProgress)
//            } else {
//                binding.mProgressBar.setVisibility(View.GONE)
//            }
//        }
//
//        override fun onShowFileChooser(
//            webView: WebView,
//            filePathCallback: ValueCallback<Array<Uri>>,
//            fileChooserParams: FileChooserParams
//        ): Boolean {
//            if (jsUtils != null) {
//                if (jsUtils.getOpenType() == JsUtils.Companion.OPEN_CAMERA) {
//                    PermissionUtils.permission(
//                        Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE,
//                        Manifest.permission.WRITE_EXTERNAL_STORAGE
//                    ).callback { isAllGranted, granted, deniedForever, denied ->
//                        if (!deniedForever.isEmpty()) {
//                            ToastUtils.show(R.string.twentythree_no_camera_permission)
//                            AppUtils.launchAppDetailsSettings()
//                            filePathCallback.onReceiveValue(null)
//                        } else if (!isAllGranted) {
//                            ToastUtils.show(R.string.twentythree_no_camera_permission)
//                            filePathCallback.onReceiveValue(null)
//                        } else {
//                            this@WebViewActivity.filePathCallback = filePathCallback
//                            IDCardCamera.create(this@WebViewActivity)
//                                .openCamera(IDCardCamera.TYPE_IDCARD_FRONT)
//                            AutoSizeConfig.getInstance().stop(this@WebViewActivity)
//                        }
//                    }.request()
//                } else if (jsUtils.getOpenType() == JsUtils.Companion.OPEN_GALLERY) {
//                    openGallery(filePathCallback)
//                }
//            } else {
//                filePathCallback.onReceiveValue(null)
//            }
//            return true
//        }
//    }
//
//    protected fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        if (resultCode == IDCardCamera.RESULT_CODE && filePathCallback != null) {
//            if (data == null) {
//                filePathCallback.onReceiveValue(null)
//                filePathCallback = null
//            }
//            val imagePath: String = IDCardCamera.getImagePath(data)
//            if (!TextUtils.isEmpty(imagePath)) {
//                if (requestCode == IDCardCamera.TYPE_IDCARD_FRONT) {
//                    //???????????????
//                    val file = File(imagePath)
//                    if (file.exists()) {
//                        filePathCallback.onReceiveValue(arrayOf(Uri.fromFile(file)))
//                    } else {
//                        filePathCallback.onReceiveValue(null)
//                    }
//                    filePathCallback = null
//                }
//            }
//        } else if (requestCode == this@WebViewActivity.requestCode) {
//            setResult(resultCode, data)
//            finish()
//        } else if (data != null) {
//            val index: Int = requestCode % JsUtils.Companion.START_CONTACT
//            if (index == 1 || index == 2) {
//                val contact: Array<String> = DataBaseHelper.queryContactByUri(data.getData())
//                if (contact == null || contact.size == 0) {
//                    ToastUtils.show(StringUtils.getPermissionTips(Manifest.permission.READ_CONTACTS))
//                } else {
//                    jsUtils!!.sendContact(binding.mWebView, contact, index)
//                }
//            }
//        }
//        if (filePathCallback != null) {
//            filePathCallback.onReceiveValue(null)
//            filePathCallback = null
//        }
//    }
//
//    private fun openGallery(filePathCallback: ValueCallback<Array<Uri>>) {
//        PermissionUtils.permission(
//            Manifest.permission.READ_EXTERNAL_STORAGE
//        ).callback { isAllGranted, granted, deniedForever, denied ->
//            if (!deniedForever.isEmpty()) {
//                ToastUtils.show(StringUtils.getPermissionTips(Manifest.permission.READ_EXTERNAL_STORAGE))
//                AppUtils.launchAppDetailsSettings()
//                filePathCallback.onReceiveValue(null)
//            } else if (!isAllGranted) {
//                ToastUtils.show(StringUtils.getPermissionTips(Manifest.permission.READ_EXTERNAL_STORAGE))
//                filePathCallback.onReceiveValue(null)
//            } else {
//                PictureSelector.create(this@WebViewActivity)
//                    .openGallery(PictureMimeType.ofImage()) // ??????.PictureMimeType.ofAll()?????????.ofImage()?????????.ofVideo()?????????.ofAudio()
//                    .imageEngine(GlideEngine.Companion.getInstance()) // ??????????????????????????????????????????
//                    .isWeChatStyle(true) // ????????????????????????????????????
//                    .isPageStrategy(true) // ???????????????????????? & ??????????????????????????????
//                    .maxSelectNum(1) // ????????????????????????
//                    .minSelectNum(1) // ??????????????????
//                    .imageSpanCount(4) // ??????????????????
//                    .isReturnEmpty(false) // ????????????????????????????????????????????????
//                    .closeAndroidQChangeWH(true) //??????????????????????????????????????????,?????????true
//                    .setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED) // ????????????Activity????????????????????????????????????
//                    .isOriginalImageControl(false) // ????????????????????????????????????????????????true?????????????????????????????????????????????????????????????????????????????????
//                    .selectionMode(PictureConfig.SINGLE) // ?????? or ??????
//                    .isPreviewImage(true) // ?????????????????????
//                    .isCamera(true) // ????????????????????????
//                    //.isZoomAnim(true)// ?????????????????? ???????????? ??????true
//                    //.isEnableCrop(true)// ????????????
//                    .isCompress(true) // ????????????
//                    .synOrAsy(true) //??????true?????????false ?????? ????????????
//                    //.withAspectRatio(1, 1)// ???????????? ???16:9 3:2 3:4 1:1 ????????????
//                    //.freeStyleCropEnabled(true)// ????????????????????????
//                    //.showCropGrid(true)// ?????????????????????????????? ???????????????????????????false
//                    .cutOutQuality(90) // ?????????????????? ??????100
//                    .minimumCompressSize(0) // ????????????kb??????????????????
//                    // .forResult(PictureConfig.CHOOSE_REQUEST);
//                    .forResult(object : OnResultCallbackListener<LocalMedia?>() {
//                        fun onResult(result: List<LocalMedia>?) {
//                            if (result != null && !result.isEmpty()) {
//                                val media: LocalMedia = result[0]
//                                val path: String
//                                path = if (media.isCut() && !media.isCompressed()) {
//                                    // ?????????
//                                    media.getCutPath()
//                                } else if (media.isCompressed() || media.isCut() && media.isCompressed()) {
//                                    // ?????????,???????????????????????????,??????????????????????????????
//                                    media.getCompressPath()
//                                } else {
//                                    // ??????
//                                    media.getPath()
//                                }
//                                val file = File(path)
//                                if (file.exists()) {
//                                    filePathCallback.onReceiveValue(arrayOf(Uri.fromFile(file)))
//                                } else {
//                                    filePathCallback.onReceiveValue(null)
//                                }
//                            }
//                        }
//
//                        fun onCancel() {
//                            filePathCallback.onReceiveValue(null)
//                        }
//                    })
//            }
//        }.request()
//    }
//
//    protected fun onResume() {
//        super.onResume()
//        binding.mWebView.onResume()
//        AutoSizeConfig.getInstance().isStop()
//        AutoSizeConfig.getInstance().restart()
//    }
//
//    protected fun onPause() {
//        super.onPause()
//        binding.mWebView.onPause()
//    }
//
//    fun onBackPressed() {
//        if (binding.mWebView.canGoBack()) {
//            binding.mWebView.goBack()
//        } else {
//            finish()
//        }
//    }
//
//    protected fun onDestroy() {
//        super.onDestroy()
//        if (null != binding.mWebView) {
//            val parent: ViewParent = binding.mWebView.getParent()
//            if (parent != null) {
//                (parent as ViewGroup).removeView(binding.mWebView)
//            }
//            binding.mWebView.stopLoading()
//            binding.mWebView.clearFormData()
//            binding.mWebView.clearHistory()
//            binding.mWebView.removeAllViews()
//            binding.mWebView.destroy()
//        }
//    }
//
//    companion object {
//        private val URL_BASE: String = BuildConfig.BASE_H5
//        val URL_PROFILE = URL_BASE + "/#/myProfile"
//        val URL_OCR = URL_BASE + "/#/baseInfo"
//        val URL_BASE_INFO = URL_BASE + "/#/baseInfo"
//        val URL_CONTACT_INFO = URL_BASE + "/#/connectInfo"
//        val URL_WORK_INFO = URL_BASE + "/#/workInfo"
//        val URL_BANK_INFO = URL_BASE + "/#/bankCardInfo"
//        val URL_REPAY = URL_BASE + "/#/repay"
//        val URL_PERIOD = URL_BASE + "/#/period"
//        val URL_RECORD = URL_BASE + "/#/record"
//        fun load(activity: AppCompatActivity, startParams: StartParams) {
//            activity.startActivityForResult(
//                Intent(
//                    activity,
//                    WebViewActivity::class.java
//                ).putExtra("bundle", startParams.bundle), startParams.bundle.getInt("request_code")
//            )
//        }
//    }
//}