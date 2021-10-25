package com.neutron.deloan.facedetection

import ai.advance.liveness.lib.GuardianLivenessDetectionSDK
import ai.advance.liveness.lib.LivenessResult
import ai.advance.liveness.sdk.activity.LivenessActivity
import android.Manifest
import android.content.Intent
import android.provider.ContactsContract
import com.leaf.library.StatusBarUtil
import com.neutron.deloan.R
import com.neutron.deloan.base.BaseActivity
import com.neutron.deloan.bean.AdvanceLicenseResult
import com.neutron.deloan.bean.BaseResponse
import com.neutron.deloan.confirm.ConfirmActivity
import com.neutron.deloan.utils.PreferencesHelper
import com.neutron.deloan.utils.Slog
import com.neutron.deloan.utils.startTo
import com.neutron.deloan.utils.toast
import com.permissionx.guolindev.PermissionX
import kotlinx.android.synthetic.main.activity_face_detection.*
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
        StatusBarUtil.setTransparentForWindow(this)
        initToolbar(getString(R.string.face_d),true)
        btn_next.setOnClickListener {
            if(isSupprotFace){
                PermissionX.init(this)
                    .permissions(listOf(Manifest.permission.READ_CONTACTS,Manifest.permission.CAMERA))
                    .onExplainRequestReason { scope, deniedList -> scope.showRequestReasonDialog(deniedList, getString(R.string.not_pp), getString(R.string.dialog_ok), getString(R.string.dialog_cancel)) }
                    .onForwardToSettings { scope, deniedList -> scope.showForwardToSettingsDialog(deniedList, getString(R.string.not_pp), getString(R.string.dialog_ok), getString(R.string.dialog_cancel)) }
                    .request { allGranted, _, _ ->
                        if (allGranted) {
                            uploadAppAndPhone(this)
                            val intent = Intent(this, LivenessActivity::class.java)
                            startActivityForResult(intent, REQUEST_LIVENESS_CODE)
                        } else {
                            toast(R.string.not_pp)
                        }
                    }
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