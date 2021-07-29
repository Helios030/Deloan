package com.neutron.deloan

import android.os.Handler
import android.os.Looper
import com.neutron.deloan.base.BaseActivity
import com.neutron.deloan.base.IBaseActivity
import com.neutron.deloan.main.MainActivity
import com.neutron.deloan.net.RetrofitUtil
import com.neutron.deloan.utils.*

import com.ronal.crazy.util.AfPointUtils
import kotlinx.android.synthetic.main.activity_welcome.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class WelcomeActivity : IBaseActivity() {

    var startTime = 1000L

    override fun getLayoutId(): Int {
        return R.layout.activity_welcome
    }

    override fun initData() {
    }


    override fun initView() {





        iv_welcome.post {

            Handler(Looper.getMainLooper()).postDelayed({
                if (PreferencesHelper.isFirstStart()) {
                    AfPointUtils.userAppsFlyerReturnDataEvent(Constants.AF_APP_INSTALL, Constants.EVENT_CODE_INSTALL, "", this)
                    uploadAppDownload()
                    PreferencesHelper.setFirstStart(false)
                }
                if (PreferencesHelper.getUserID().isNullOrEmpty()) {
                    startTo(PermisonActivity::class.java,true)
                } else {
                    startTo(MainActivity::class.java,true)
                }

            },startTime)

        }

    }
    private fun uploadAppDownload() {
        GlobalScope.launch(Dispatchers.Main) {
            val map = HashMap<String, Any>();
            map["uuid"] = DeviceFactory.getInstance().getUUID() ?: ""
            map["imei"] = PreferencesHelper.getIMEI()
            map["referrer"] = PreferencesHelper.getReferrer()
            map["download_time"] = SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Date())
            try {
                val result=   RetrofitUtil.service.uploadAppFirst(Utils.createBody(Utils.createCommonParams(map)))
                Slog.d("第一次上传结果  $result")
            } catch (e: Exception) {
                e.printStackTrace()
                Slog.e("第一次上传结果  $e")
            }
        }
    }


}