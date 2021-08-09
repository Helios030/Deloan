package com.neutron.deloan


import ai.advance.liveness.lib.GuardianLivenessDetectionSDK
import ai.advance.liveness.lib.Market
import android.app.Application
import android.content.Context
import com.appsflyer.AppsFlyerConversionListener
import com.appsflyer.AppsFlyerLib
import com.neutron.deloan.utils.Constants
import com.neutron.deloan.utils.Slog
import com.ronal.crazy.util.AfPointUtils


class NApplication : Application() {


    companion object {
        lateinit var sContext: Context
    }

    override fun onCreate() {
        super.onCreate()
        sContext = this
        initAF()
        AfPointUtils.trackEvent(Constants.AF_APP_ACTIVATION, this)
        val isDeBug = false
        Slog.d("isDeBug  $isDeBug")
        Slog.getSettings().setLogEnable(isDeBug).setBorderEnable(isDeBug)

        GuardianLivenessDetectionSDK.init(this, Market.Thailand)

    }




    private fun initAF() {
        if (Constants.AF_APP_KEY.isNotEmpty()) {
            val conversionDataListener: AppsFlyerConversionListener =
                object : AppsFlyerConversionListener {
                    override fun onInstallConversionDataLoaded(map: Map<String, String>) {}
                    override fun onInstallConversionFailure(s: String) {}
                    override fun onAppOpenAttribution(map: Map<String, String>) {}
                    override fun onAttributionFailure(s: String) {}
                }
            AppsFlyerLib.getInstance()
                .init(Constants.AF_APP_KEY, conversionDataListener, applicationContext)
            AppsFlyerLib.getInstance().startTracking(this)
            AppsFlyerLib.getInstance().reportTrackSession(this)
        }
    }

}