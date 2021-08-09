package com.neutron.deloan.web

import android.app.Activity
import android.content.Intent
import android.content.pm.ActivityInfo
import android.database.Cursor
import android.net.Uri
import android.provider.ContactsContract
import android.text.TextUtils
import android.view.KeyEvent
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.webkit.*
import android.widget.LinearLayout
import com.just.agentweb.AgentWeb
import com.luck.picture.lib.PictureSelector
import com.luck.picture.lib.config.PictureConfig
import com.luck.picture.lib.config.PictureMimeType
import com.neutron.deloan.R
import com.neutron.deloan.base.IBaseActivity
import com.neutron.deloan.bean.LoanStatusResult
import com.neutron.deloan.facedetection.FaceDetectionActivity
import com.neutron.deloan.utils.*
import com.neutron.deloan.view.CameraDialog
import com.neutron.deloan.webview.JavaScriptFunction
import com.ronal.camera.camera.IDCardCamera
import kotlinx.android.synthetic.main.activity_web_view.*
import java.io.File


class WebViewActivity : IBaseActivity() {
    var mAgentWeb: AgentWeb? = null
    var loanStatusResult: LoanStatusResult? = null
    override fun getLayoutId(): Int {
        return R.layout.activity_web_view
    }

    fun initData() {
        Slog.d("initData $intent")
        val intent = intent
        if (intent != null) {
            val uri = intent.getStringExtra(Constants.Intent_URI)
            val isMain = intent.getBooleanExtra(Constants.IS_MAIN, true)
            loanStatusResult =
                intent.getSerializableExtra(Constants.LOAN_STATUS_RESULT) as LoanStatusResult?
            Slog.d("测试  ====     uri $uri  isMain  $isMain  loanStatusResult $loanStatusResult")
            uri?.let {
                loadURI(it, isMain)
            }

        }
    }

    override fun initView() {
//        var titleStr = ""
//        intent?.getStringExtra(Constants.ACTIVITY_ACTION_TITLE)?.let {
//            titleStr = it
//        }
//        initToolbar(titleStr,true)
        initData()
//        setStatusBarColor(this, R.color.color_gray_f7)


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


    var filePathCall: ValueCallback<Array<Uri>>? = null

    val mWebViewClient: com.just.agentweb.WebChromeClient =
        object : com.just.agentweb.WebChromeClient() {
            override fun onShowFileChooser(
                webView: WebView?,
                filePathCallback: ValueCallback<Array<Uri>>?,
                fileChooserParams: FileChooserParams?
            ): Boolean {
                filePathCall = filePathCallback

                Slog.d("onShowFileChooser ${javaScriptFunction==null}    ")

                javaScriptFunction?.let {
                    Slog.d("onShowFileChooser ${it.getOpenType()}    ")
                    when (it.getOpenType()) {
                        JavaScriptFunction.OPEN_CAMERA -> {
                            openCamera()
                        }
                        JavaScriptFunction.OPEN_GALLERY -> {
                            selectPhoto()
                        }
                        else -> {
                            Slog.d("未知状态")
                        }


                    }


                }
                return true

            }

        }


    private fun loadURI(url: String, isMain: Boolean) {

Slog.d("loadURI  ")
        mAgentWeb = AgentWeb.with(this)
            .setAgentWebParent(fl_main, LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT))
            .useDefaultIndicator()
//            .setWebViewClient(mWebViewClient)
            .setWebChromeClient(mWebViewClient)
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
        webViewSetting?.javaScriptEnabled = true
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

        if (loanStatusResult == null) {
            javaScriptFunction = JavaScriptFunction(isMain, lisenter)
        } else {
            javaScriptFunction = JavaScriptFunction(isMain, loanStatusResult!!, lisenter)
        }
        mAgentWeb?.jsInterfaceHolder?.addJavaObject("androidUtils", javaScriptFunction);

    }

    var javaScriptFunction: JavaScriptFunction? = null


    var isNeedRef=true

    fun openCamera() {
        var dialog: CameraDialog? = null
        dialog = CameraDialog(this).setOnClickBottomListener(object :
            CameraDialog.OnClickBottomListener {
            override fun onOkClick() {
                isNeedRef=false
                IDCardCamera.create(this@WebViewActivity)
                    .openCamera(IDCardCamera.TYPE_IDCARD_FRONT)
                dialog?.dismiss()
            }
        })
        dialog.show()

        dialog.setOnDismissListener {
     if(isNeedRef){
         initData()
         isNeedRef=true

     }

        }

    }


    var PICK_CONTACT = 1

    val lisenter = object : JavaScriptFunction.Listener {
        override fun onStartContact(requestCode: Int) {
            Slog.d("开启联系人 ${PICK_CONTACT}")
            PICK_CONTACT = requestCode
            val intent = Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI)
            startActivityForResult(intent, PICK_CONTACT)


        }

        override fun onStartLive() {
            startTo(FaceDetectionActivity::class.java)
        }

        override fun setResult(resultCode: Int, isFinish: Boolean) {
            if (resultCode != JavaScriptFunction.Companion.NOTHING) {
                setResult(resultCode)
            }
            if (isFinish) {
                this@WebViewActivity.finish()
            }
        }

    }


    private fun getContacts(data: Intent?, code: Int) {
        Slog.d("解析联系人  getContacts")
        var name = ""
        var phoneNumber = ""
        val contactUri = data?.data!!
        val cursor: Cursor? = contentResolver.query(contactUri, null, null, null, null)

        cursor?.let {
            if (it.moveToFirst()) {
                name =
                    cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME))
                var hasPhone: String =
                    cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))
                val id: String =
                    cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID))
                hasPhone = if (hasPhone.equals("1", ignoreCase = true)) {
                    "true"
                } else {
                    "false"
                }
                if (java.lang.Boolean.parseBoolean(hasPhone)) {
                    val phones: Cursor = contentResolver.query(
                        ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                        null,
                        ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + id,
                        null,
                        null
                    )!!
                    while (phones.moveToNext()) {
                        phoneNumber = phones.getString(
                            phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)
                        )
                    }
                    phones.close()
                }
                cursor.close()

                phoneNumber = phoneNumber.trim()

                val contactMap = HashMap<String, String>()
                contactMap["name"] = name
                contactMap["phone"] = phoneNumber
                javaScriptFunction?.sendContact(mAgentWeb, contactMap, PICK_CONTACT)
            }
        }

    }


    fun selectPhoto() {
        PictureSelector.create(this)
            .openGallery(PictureMimeType.ofImage())// 全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
            .imageEngine(GlideEngine.createGlideEngine())// 外部传入图片加载引擎，必传项
            .isWeChatStyle(true)// 是否开启微信图片选择风格
            .isPageStrategy(true)// 是否开启分页策略 & 每页多少条；默认开启
            .maxSelectNum(1)// 最大图片选择数量
            .minSelectNum(1)// 最小选择数量
            .imageSpanCount(4)// 每行显示个数
            .isReturnEmpty(false)// 未选择数据时点击按钮是否可以返回
            .closeAndroidQChangeWH(true)//如果图片有旋转角度则对换宽高,默认为true
            .setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)// 设置相册Activity方向，不设置默认使用系统
            .isOriginalImageControl(false)// 是否显示原图控制按钮，如果设置为true则用户可以自由选择是否使用原图，压缩、裁剪功能将会失效
            .selectionMode(PictureConfig.SINGLE)// 多选 or 单选
            .isPreviewImage(true)// 是否可预览图片
            .isCamera(true)// 是否显示拍照按钮
            //.isZoomAnim(true)// 图片列表点击 缩放效果 默认true
            //.isEnableCrop(true)// 是否裁剪
            .isCompress(true)// 是否压缩
            .synOrAsy(true)//同步true或异步false 压缩 默认同步
            //.withAspectRatio(1, 1)// 裁剪比例 如16:9 3:2 3:4 1:1 可自定义
            //.freeStyleCropEnabled(true)// 裁剪框是否可拖拽
            //.showCropGrid(true)// 是否显示裁剪矩形网格 圆形裁剪时建议设为false
            .cutOutQuality(90)// 裁剪输出质量 默认100
            .minimumCompressSize(0)// 小于多少kb的图片不压缩
            .forResult(PictureConfig.CHOOSE_REQUEST)

    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        Slog.d("onActivityResult requestCode $requestCode  $resultCode")


        if(resultCode==0){
            Slog.d("重新载入网页")
            initData()
        }else  if (resultCode == Activity.RESULT_OK) {
            if (requestCode == PictureConfig.CHOOSE_REQUEST) {
                val localMediaList = PictureSelector.obtainMultipleResult(data)
                if (localMediaList != null && localMediaList.size > 0) {
                    val localMedia = localMediaList[0]
                    val imgPath = if (localMedia.isCut && !localMedia.isCompressed) {
                        localMedia.cutPath
                    } else if (localMedia.isCompressed || localMedia.isCut && localMedia.isCompressed) {
                        localMedia.compressPath
                    } else {
                        localMedia.path
                    }
                    val file = File(imgPath)
                    Slog.d("相册返回  $imgPath")
                    if (file.exists()) {
                        filePathCall?.onReceiveValue(arrayOf(Uri.fromFile(file)))
                    } else {
                        filePathCall?.onReceiveValue(null)
                    }
                }
            } else {
                Slog.d("解析联系人")
                getContacts(data, PICK_CONTACT)

            }


        }

        if (resultCode == IDCardCamera.RESULT_CODE) {
//            AutoSizeConfig.getInstance().restart()

            val imagePath = IDCardCamera.getImagePath(data)
            if (!TextUtils.isEmpty(imagePath)) {
                if (requestCode == IDCardCamera.TYPE_IDCARD_FRONT) {
                    //身份证正面
                    val file = File(imagePath)

                    Slog.d("相机回传  $imagePath")
                    Slog.d("相机回传  $file")
                    if (file.exists()) {
                        filePathCall?.onReceiveValue(arrayOf(Uri.fromFile(file)))
                    } else {
                        filePathCall?.onReceiveValue(null)
                    }
                }
            }
        }


    }


}