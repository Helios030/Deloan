package com.neutron.deloan.facedetection

import ai.advance.liveness.lib.GuardianLivenessDetectionSDK
import ai.advance.liveness.lib.LivenessResult
import ai.advance.liveness.sdk.activity.LivenessActivity
import android.content.Intent
import android.view.View
import com.neutron.deloan.R
import com.neutron.deloan.base.BaseActivity
import com.neutron.deloan.bean.AdvanceLicenseResult
import com.neutron.deloan.confirm.ConfirmActivity
import com.neutron.deloan.net.BaseResponse
import com.neutron.deloan.utils.*
import kotlinx.android.synthetic.main.activity_face_detection.*
import kotlinx.android.synthetic.main.toolbar_common.*
import java.util.*

class FaceDetectionActivity :
    BaseActivity<FaceDetectionContract.View, FaceDetectionContract.Presenter>(),
    FaceDetectionContract.View {


    override fun setPresenter(): FaceDetectionContract.Presenter {
        return FaceDetectionPresenter()
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_face_detection
    }

    override fun initData() {
        val map = HashMap<String, Any>()
        map["user_id"] = PreferencesHelper.getUserID()
        mPresenter?.getAdvancelicense(map)
        showLoading()
    }

    override fun initView() {

initToolbar(getString(R.string.face_d),true)

        btn_next.setOnClickListener {
            if(isSupprotFace){
                uploadAppAndPhone(this)
                val intent = Intent(this, LivenessActivity::class.java)
                startActivityForResult(intent, REQUEST_LIVENESS_CODE)
            }else{
                finish()
            }


        }
    }

    var isSupprotFace=true

    companion object {
        private const val REQUEST_LIVENESS_CODE = 99
        public const val ProductID = "ProductID"
    }

    override fun returnAdvancelicense(it: BaseResponse<AdvanceLicenseResult>) {
        hideLoading()
        if (it.code == "200") {
            val expireTimestamp = it.result.expireTimestamp
            val license = it.result.license

            val checkResult = GuardianLivenessDetectionSDK.setLicenseAndCheck(license)
            Slog.d("活体检测key认证 $checkResult")
            if ("SUCCESS" == checkResult) {

            }
            isSupprotFace=true
        } else {
            toast(it.message)
            isSupprotFace=false
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {

            REQUEST_LIVENESS_CODE ->
                if (LivenessResult.isSuccess()) {
                    val livenessId = LivenessResult.getLivenessId() // 本次活体id
                    PreferencesHelper.setLivenessID(livenessId)
                    Slog.d("活体认证  $livenessId  ")

                    startTo(ConfirmActivity::class.java)
                } else {

                    if (LivenessResult.getErrorMsg() != null) {
                        toast(LivenessResult.getErrorMsg())
                        Slog.d("活体认证失败  ${LivenessResult.getErrorMsg()}")
                    }

                }
        }

    }

}