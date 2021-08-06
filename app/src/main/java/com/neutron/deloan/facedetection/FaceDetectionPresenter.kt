package com.neutron.deloan.facedetection


import com.neutron.deloan.base.BasePresenter
import com.neutron.deloan.net.RetrofitUtil
import com.neutron.deloan.utils.Slog

import com.neutron.deloan.utils.Utils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*

class FaceDetectionPresenter : BasePresenter<FaceDetectionContract.View>(),
    FaceDetectionContract.Presenter {


    override fun getAdvancelicense(map: HashMap<String, Any>) {
        Slog.d("getAdvancelicense  $map")
        job = GlobalScope.launch(Dispatchers.Main) {

            try {
                Slog.d("${mView==null}")
                mView?.returnAdvancelicense(RetrofitUtil.service.getAdvancelicense(Utils.createBody(Utils.createCommonParams(map))))
            } catch (e: Exception) {
                e.printStackTrace()
                mView?.showError(e)
            }

        }
    }


}