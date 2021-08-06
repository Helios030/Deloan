package com.neutron.deloan.facedetection

import com.neutron.deloan.base.IPresenter
import com.neutron.deloan.base.IView
import com.neutron.deloan.bean.AdvanceLicenseResult
import com.neutron.deloan.bean.BaseResponse
import java.util.*

class FaceDetectionContract {


    interface View : IView {

        fun returnAdvancelicense(advancelicense: BaseResponse<AdvanceLicenseResult>)


    }

    interface Presenter : IPresenter<View> {

        fun getAdvancelicense(map: HashMap<String, Any>)


    }

}